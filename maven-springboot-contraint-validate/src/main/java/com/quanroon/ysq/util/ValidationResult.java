package com.quanroon.ysq.util;

import lombok.Data;

import java.util.Map;

/**
 * @author quanroong.ysq
 * @version 1.0.0
 * @description
 * @createtime 2020/7/14 22:36
 */
@Data
public class ValidationResult {
    // 校验结果是否有错
    private boolean hasErrors;

    // 校验错误信息
    private Map<String, String> errorMsg;
}
