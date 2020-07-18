package com.quanroon.ysq.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.*;

/**
 * @author quanroong.ysq
 * @version 1.0.0
 * @description
 * @createtime 2020/7/14 21:59
 */
@Data
@RequiredArgsConstructor(staticName = "defineOf")
@NoArgsConstructor
public class User {

    @NonNull
    @NotBlank(message="用户名不能为空")
    private String name;

    @NonNull
    @Max(100)
    @Min(0)
    private Integer age;

    @Pattern(regexp="^[0-9]{1,2}$",message="电话是整数")
    private String phone;

    @NonNull
    private String sex;
}
