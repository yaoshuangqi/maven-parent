package com.ysq.service;

import com.ysq.entity.page.PageParams;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.Configuration;
import org.springframework.stereotype.Component;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

/**
 * 手撸分页插件
 * @Auther: 彭清龙
 * @Date: 2019/3/29 16:02
 */
@Intercepts({
        @Signature(type = StatementHandler.class,
                   method = "prepare",
                   args = {Connection.class, Integer.class})})
@Component
public class PagePlugin implements Interceptor {

    private Integer defaultPageNo;      // 默认页码
    private Integer defaultPageSize;    // 默认每页条数
    private Boolean defaultUseFlag;     // 默认是否启用插件
    private Boolean defaultCheckFlag;   // 默认是否检测页码参数
    private Boolean defaultCleanOrderBy;// 默认是否清除最后一个order by后的语句

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler stmtHandler = (StatementHandler) this.getUnProxyObject(invocation.getTarget());
        MetaObject metaObject = SystemMetaObject.forObject(stmtHandler);
        String sql = (String) metaObject.getValue("delegate.boundSql.sql");
        MappedStatement mappedStatement = (MappedStatement)metaObject.getValue("delegate.mappedStatement");

        // 不是select 语句
        if(!this.checkSelect(sql)){
            return invocation.proceed();
        }
        BoundSql boundSql = (BoundSql) metaObject.getValue("delegate.boundSql");
        Object parameterObject = boundSql.getParameterObject();
        PageParams pageParams = this.getPageParamsForParamObj(parameterObject);
        if(pageParams == null){// 冒得分页
            return invocation.proceed();
        }

        // 判断配置中是否启用分页功能
        Boolean useFlag = pageParams.getUseFlag() == null ? this.defaultUseFlag : pageParams.getUseFlag();
        if(!useFlag){ // 未启用分页插件
            return invocation.proceed();
        }

        // 获取相关配置参数
        Integer pageNo = pageParams.getPageNo() == null ? defaultPageNo : pageParams.getPageNo();
        Integer pageSize = pageParams.getPageSize() == null ? defaultPageSize : pageParams.getPageSize();
        Boolean checkFlag = pageParams.getCheckFlag() == null ? defaultCheckFlag : pageParams.getCheckFlag();
        Boolean cleanOrderBy = pageParams.getCleanOrderBy() == null ? defaultCleanOrderBy : pageParams.getCleanOrderBy();

        // 计算总条数
        int total = this.getTotal(invocation, metaObject, boundSql, cleanOrderBy);
        // 回填总条数到分页参数
        pageParams.setTotal(total);
        // 计算总页数
        int totalPage = total % pageSize == 0 ? total / pageSize : total / pageSize + 1;
        // 回填总页数到分页参数
        pageParams.setTotalPage(totalPage);
        // 检查当前页码的有效性
        //this.checkPage(checkFlag, pageNo, totalPage);
        // 修改sql
        return this.preparedSQL(invocation, metaObject, boundSql, pageNo, pageSize);
    }

    @Override
    public Object plugin(Object o) {
        // 生成代理对象
        return Plugin.wrap(o,this);
    }

    @Override
    public void setProperties(Properties properties) {
        defaultPageNo = 1;
        defaultPageSize = 10;
        defaultUseFlag = false;
        defaultCheckFlag = true;
        defaultCleanOrderBy = true;
    }


    /**
     *
     * 从代理对象中分离出真实对象
     * @author 彭清龙
     * @date 2019/3/29 16:27
     * @param target
     * @return java.lang.Object
     */
    private Object getUnProxyObject(Object target){
        MetaObject metaStatementHandler = SystemMetaObject.forObject(target);

        // 分离代理对象链
        Object object = null;
        while (metaStatementHandler.hasGetter("h")){
            object = metaStatementHandler.getValue("h");
            metaStatementHandler = SystemMetaObject.forObject(target);
        }
        if(object == null){
            return target;
        }
        return object;
    }

    /**
     *
     * 判断是否是sql语句
     * @author 彭清龙
     * @date 2019/3/29 16:30
     * @param sql
     * @return boolean
     */
    private boolean checkSelect(String sql){
        String trimSql = sql.trim();
        int idx = trimSql.toLowerCase().indexOf("select");
        return idx == 0;
    }

    /**
     *
     * 分离出分页参数
     * @author 彭清龙
     * @date 2019/3/29 17:17
     * @param parameterObject
     * @return com.quanroon.atten.entity.page.PageParams
     */
    public PageParams getPageParamsForParamObj(Object parameterObject)throws Exception{
        PageParams pageParams = null;
        if(parameterObject == null){
            return null;
        }

        // 处理map参数, 多个匿名参数和@Param注解参数都是map
        if(parameterObject instanceof Map){
            Map<String, Object> paramMap = (Map<String, Object>)parameterObject;
            Set<String> keySet = paramMap.keySet();
            Iterator<String> iterator = keySet.iterator();
            while (iterator.hasNext()){
                String key = iterator.next();
                Object value = paramMap.get(key);
                if(value instanceof PageParams){
                    return (PageParams) value;
                }
            }
        }else if(parameterObject instanceof PageParams){// 参数是或者继承了PageParams
            return (PageParams) parameterObject;
        }else{// 从实体中尝试读取分页参数

            // 获得包括父类的所有字段属性
            List<Field> fields = new ArrayList<>() ;
            Class tempClass = parameterObject.getClass();
            while (tempClass != null) {//当父类为null的时候说明到达了最上层的父类(Object类).
                fields.addAll(Arrays.asList(tempClass .getDeclaredFields()));
                tempClass = tempClass.getSuperclass(); //得到父类,然后赋给自己
            }

            for (Field field : fields){
                if(field.getType() == PageParams.class){
                    PropertyDescriptor pd = new PropertyDescriptor(field.getName(), parameterObject.getClass());
                    Method readMethod = pd.getReadMethod();
                    return (PageParams) readMethod.invoke(parameterObject);
                }
            }
        }
        return pageParams;
    }

    /**
     *
     * 获取总条数
     * @author 彭清龙
     * @date 2019/4/1 9:46
     * @param ivt, metaObject, boundSql, cleanOrderBy
     * @return int
     */
    private int getTotal(Invocation ivt, MetaObject metaObject, BoundSql boundSql, Boolean cleanOrderBy)throws Throwable{
        // 获取当前的mappedStatement
        MappedStatement mappedStatement = (MappedStatement)metaObject.getValue("delegate.mappedStatement");
        // 配置对象
        Configuration config = mappedStatement.getConfiguration();
        // 当前需要执行的sql
        String sql = (String) metaObject.getValue("delegate.boundSql.sql");
        // 去掉最后的order by 语句
        if(cleanOrderBy){
            sql = this.cleanOrderByForSql(sql);
        }
        // 改写为统计总数的sql
        String countSql = "select count(1) as total from (" + sql + ") $_paging";

        // 获取拦截方法参数, 根据插件签名, 知道是connection对象
        Connection connection = (Connection) ivt.getArgs()[0];
        PreparedStatement ps = null;
        int total = 0;
        try{
            // 预编译统计总数sql
            ps = connection.prepareStatement(countSql);
            // 构建统计总数sql
            BoundSql countBoundSql = new BoundSql(config, countSql, boundSql.getParameterMappings(), boundSql.getParameterObject());
            // 参数转移一哈 不然报错
            for (ParameterMapping mapping : boundSql.getParameterMappings()){
                String key = mapping.getProperty();
                if(boundSql.hasAdditionalParameter(key)){
                    countBoundSql.setAdditionalParameter(key, boundSql.getAdditionalParameter(key));
                }

            }
            // 构建mybatis的parameterHandler 用来设置总数sql的参数
            ParameterHandler handler = new DefaultParameterHandler(mappedStatement, boundSql.getParameterObject(), countBoundSql);
            // 设置总数sql
            handler.setParameters(ps);
            // 执行查询
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                total = rs.getInt("total");
            }
        }finally {
            // 不关闭链接  后续sql还要执行
            if(ps != null){
                ps.close();
            }
        }
        return total;
    }

    /**
     *
     * 去掉最后的order by 语句
     * @author 彭清龙
     * @date 2019/4/1 9:47
     * @param sql
     * @return java.lang.String
     */
    private String cleanOrderByForSql(String sql){
        StringBuilder sb = new StringBuilder(sql);
        String newSql = sql.toLowerCase();
        // 如果没有order语句, 则直接返回
        if(newSql.indexOf("order") == -1){
            return sql;
        }
        int index = newSql.lastIndexOf("order");
        return sb.substring(0,index).toString();
    }

    /**
     *
     * 检查页码
     * @author 彭清龙
     * @date 2019/4/1 10:33
     * @param checkFlag, pageNum, pageTotal
     * @return void
     */
    private void checkPage(boolean checkFlag, Integer pageNum, Integer pageTotal) throws Throwable{
        // 检查页码是否合法
        if(pageNum > pageTotal){
            throw new Exception("查询失败，查询页码【" + pageNum + "】大于总页数【"+ pageTotal +"】!!");
        }
    }

    /**
     *
     * 预编译改写后的sql, 并设置分页参数
     * @author 彭清龙
     * @date 2019/4/1 10:45
     * @param invocation, metaObject, boundSql, pageNum, pageSize
     * @return java.lang.Object
     */
    private Object preparedSQL(Invocation invocation, MetaObject metaObject,
                               BoundSql boundSql, int pageNum, int pageSize)throws Exception{
        // 获取当前需要执行的sql
        String sql = boundSql.getSql();
        String newSql = "select * from (" + sql + ") $_paging_table limit ?, ?";
        // 修改当前需要执行的sql
        metaObject.setValue("delegate.boundSql.sql", newSql);
        // 执行编译, 详单与statementHandler执行了prepared()方法, 这个时候, 就剩下两个分页参数没有设置
        Object statementObj = invocation.proceed();
        this.preparePageDataParams((PreparedStatement) statementObj, pageNum, pageSize);
        return statementObj;
    }

    /**
     *
     * 使用preparedStatement预编译两个分页参数, 如果数据库的规则不一样, 需要改写设置的参数规则
     * @author 彭清龙
     * @date 2019/4/1 10:46
     * @param ps, pageNum, pageSize
     * @return void
     */
    private void preparePageDataParams(PreparedStatement ps, int pageNum, int pageSize) throws Exception {
        // prepared()方法编译sql, 由于mybatis上下文没有分页参数的信息, 所以这里需要设置这两个参数
        int index = ps.getParameterMetaData().getParameterCount();
        // 最后两个是分页参数
        ps.setInt(index - 1, (pageNum - 1) * pageSize);// 开始行
        ps.setInt(index, pageSize);// 限制条数
    }


}
