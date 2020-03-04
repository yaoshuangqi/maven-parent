package com.ysq.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author quanroon.ysq
 * @version 1.0.0
 * @date 2020/3/3 10:53
 * @content
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    //是否开启swagger，正式环境一般是需要关闭的，可根据springboot的多环境配置进行设置
    @Value("${swagger.enabled}")
    Boolean swaggerEnabled;

    @Bean
    public Docket createRestApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("app端接口调用")
                .apiInfo(apiInfo())
                .enable(swaggerEnabled)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.ysq.web.app")) //要扫描的API(Controller)基础包
                .paths(PathSelectors.regex("/api.*")) // 指定路径处理PathSelectors.any()代表所有的路径
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .title("APP用户信息API文档")
            .description("这里除了查看接口功能外，还提供了调试测试功能")
            //.termsOfServiceUrl("http://www.java-mindmap.com/")//链接
            .contact(new Contact("yaoshuangqi", "https://www.baidu.com/", "499452441@qq.com"))
            .version("1.0")
            .build();
    }

    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    @Bean
    public Docket restApi2() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("ios端接口调用")
                .apiInfo(apiInfo("IOS用户信息API文档", "2.0"))
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.ysq.web.ios"))
                .paths(PathSelectors.regex("/ios.*"))
                .build();
    }

    /**
     * 创建该API的基本信息（这些基本信息会展现在文档页面中）
     * 访问地址：http://ip:port/swagger-ui.html
     *
     * @return
     */
    private ApiInfo apiInfo(String title, String version) {
        return new ApiInfoBuilder()
                .title(title)
                .description("更多请关注: https://blog.csdn.net/xqnode")
                .termsOfServiceUrl("https://blog.csdn.net/xqnode")
                .contact(new Contact("xqnode", "https://blog.csdn.net/xqnode", "xiaqingweb@163.com"))
                .version(version)
                .build();
    }
}
