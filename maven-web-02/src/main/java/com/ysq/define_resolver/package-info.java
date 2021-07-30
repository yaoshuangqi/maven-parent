/**
 * @content
 * @author quanroon.ysq
 * @date 2020/11/7 11:37
 * @version 1.0.0
 */
package com.ysq.define_resolver;

/**
* 场景
 * 有很多情况下，想把某个信息设置成全局的，在任何地方都可以当作参数传入到方法体中，其实这种情况下，就类似于springMvc中，用户信息都会通过request
 * 来获取，说白了，就是模仿了springMvc中的handler解析器来做的
 *
 * 每个接口在被调用时，很可能需要调用该接口的用户信息，每次再去数据库查询该数据信息，势必会造成代码的大量重复，且还容易出错。
 *
 * 应用：把该次调用者用户的信息当做参数传到对应的方法。
 *
 * 比如：根据token获得到用户信息，将用户信息传到对应请求的method。
 *
 * 实现自定义参数解析器步骤：1、自定义注解 2、自定义参数解析器 3、注册
*/