package com.quanroon.ysq.annoation;

import com.quanroon.ysq.config.GpImportSelector;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author quanroong.ysq
 * @version 1.0.0
 * @description
 * @createtime 2020/8/16 17:25
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@AutoConfigurationPackage
@Import(GpImportSelector.class)
public @interface EnableAutoImport {
}
