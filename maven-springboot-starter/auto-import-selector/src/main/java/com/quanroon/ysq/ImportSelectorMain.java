package com.quanroon.ysq;

import com.quanroon.ysq.annoation.EnableAutoImport;
import com.quanroon.ysq.config.FirstClass;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author quanroong.ysq
 * @version 1.0.0
 * @description 自动装配：通过扫描约定目录下的文件进行解析，解析完成之后把得到的Configuration配置类通过ImportSelector进行导入，从而完成Bean的自动装配
 * @createtime 2020/8/16 17:20
 */
@SpringBootApplication
@EnableAutoImport
public class ImportSelectorMain {
    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(ImportSelectorMain.class, args);
        FirstClass bean = applicationContext.getBean(FirstClass.class);
        bean.test();
    }
}
