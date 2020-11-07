/**
 * @content  限速器
 * SpringBoot中使用Guava基于令牌桶实现限流
 * @author quanroon.ysq
 * @date 2020/10/24 16:57
 * @version 1.0.0
 */
package com.quanroon.ysq.rate_limiter;
/**
* @Description: 通俗的理解就是，有一个固定大小的水桶，水龙头一直按照一定的频率往里面滴水。水满了，就不滴了。客户端每次进行请求之前，都要先尝试从水桶里面起码取出“一滴水”，
 * 才能处理业务。因为桶的大小固定，水龙头滴水频率固定。从而也就保证了数据接口的访问流量。
* @Author: quanroon.yaosq
* @Date: 2020/10/24 16:58
* @Param:
* @Return:
*/