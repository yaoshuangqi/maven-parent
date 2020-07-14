package com.quanroon.ysq.annoation;

import com.quanroon.ysq.annoation.ValidateParam;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author quanroong.ysq
 * @version 1.0.0
 * @description 校验注解的实现类
 * @createtime 2020/7/14 22:55
 */
public class ConstraintValidate implements ConstraintValidator<ValidateParam, Object> {
    /**
     * 初始化
     * @param constraintAnnotation
     */
    @Override
    public void initialize(ValidateParam constraintAnnotation) {

        System.out.println("初始化。。。");
    }

    /**
     * 校验方法，成功：true 失败：false
     * @param value
     * @param context
     * @return
     */
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        System.out.println("开始根据给定的条件进行校验。。。");
        return false;
    }
}
