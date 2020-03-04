package com.ysq.entity;

/**
 * @author quanroon.ysq
 * @version 1.0.0
 * @date 2020/3/4 15:14
 * @content
 */

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 响应实体
 * @author yaoshuangqi
 * Builder链式编程实例
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public class DemoResp {

    @ApiModelProperty(name="code",value="编码",example="200")
    String code;

    @ApiModelProperty(name="name",value="名称",example="yaoshuangqi")
    String name;

    @ApiModelProperty(name="remark",value="备注",example="www.baidu.com")
    String remark;
}
