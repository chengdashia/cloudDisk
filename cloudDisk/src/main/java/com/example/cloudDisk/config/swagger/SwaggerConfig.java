package com.example.cloudDisk.config.swagger;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
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

/**
 * @author :成大事
 * @since :2022-06-04 12:36:15
 * EnableSwagger2   开启swagger2
 * EnableKnife4j    该注解是knife4j提供的增强注解,Ui提供了例如动态参数、参数过滤、接口排序等增强功能,如果你想使用这些增强功能就必须加该注解，否则可以不用加
 */
@Configuration
@EnableKnife4j
public class SwaggerConfig {

    /**
     *  api的主页显示信息
     */
    private static final ApiInfo API_INFO;

    /**
     * swagger激活环境
     */
    @Value(value = "${knife4j.enable}")
    public boolean enable;

    static {
        API_INFO = new ApiInfoBuilder()
                .title("企业用户API接口")
                .description("API接口文档")
                .termsOfServiceUrl("https://blog.chengdashi.cn")
                .contact(new Contact("成大事",
                        "https://blog.chengdashi.cn",
                        "1847085602@qq.com"))
                .version("1.0")
                .build();
    }


    /**配置swagger的Docker的bean实例*/
    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("api1")
                .apiInfo(API_INFO)
                .enable(enable)
//                .enable(flag)   //enable 是否启动swagger，如果为false，则swagger不能浏览器中访问
                .select()
                //RequestHandlerSelectors  配置要扫描接口的方式
                //basePackage :指定的包
                //any(） :扫描全部
                //none :都不扫描
                //withClassAnnotation :扫描类上的注解  参数是一个注解的反射对象
                //withMethodAnnotation :扫描类上的注解
                .apis(RequestHandlerSelectors.basePackage("com.example.cloudDisk.controller"))
                //path()  过滤什么路径
                .paths(PathSelectors.any())
                .build();
    }


    /**如果要新增一个分组：api2*/
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                // 配置分组名
                .groupName("api2")
                .apiInfo(API_INFO)
                .enable(enable)
                .select()
                // 设置扫描包的地址 : com.hanliy.controller2
                .apis(RequestHandlerSelectors.basePackage("com.example.cloudDisk.controller"))
                .paths(PathSelectors.any())
                .build();
    }


}