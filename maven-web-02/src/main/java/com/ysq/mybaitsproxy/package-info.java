/**
 * @content mybaits处理sql，采用基于接口，jdk动态代理生成子类完成的，
 * 其主要再处理 sql前后，使用拦截器，并结合代理模式将四类拦截器用一个拦截器管理起来，用责任链模式解决了要单独判断哪类拦截逻辑用什么拦截器的判断逻辑
 * @author quanroon.ysq
 * @date 2020/9/14 16:41
 * @version 1.0.0
 */
package com.ysq.mybaitsproxy;