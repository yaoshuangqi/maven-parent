package com.ysq.entity.page;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
* @Description:
* @Author: quanroon.yaosq
* @Date: 2020/9/14 11:50
* @Param:
* @Return:
*/
@Data
public class PageParams {

    private Integer pageNo;         // 当前页码
    private Integer pageSize;       // 每页数量
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Boolean useFlag;        // 分页开关
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Boolean checkFlag;      // 页码有效性
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Boolean cleanOrderBy;   // 清除最后order by
    private Integer total;          // 总条数 插件回传
    private Integer totalPage;      // 总页数 插件回传

    private List list;

    public PageParams page(List list){
        this.list = list;
        return this;
    }

    public void openPage(){
        this.useFlag = true;
        this.checkFlag = false;
        this.cleanOrderBy = true;
    }

    public void closePage(){
        this.useFlag = false;
        this.cleanOrderBy = false;
        this.checkFlag = false;
    }
}
