package com.hanfu.product.center.config;


import com.hanfu.product.center.config.MyInterceptor;
import com.hanfu.product.center.intercepter.AuthorityInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.ErrorProperties.IncludeStacktrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;

@Configuration
public class WebConfigurer extends WebMvcConfigurationSupport {

    @Autowired
    private AuthorityInterceptor authorityInterceptor;
    @Bean

    public MyInterceptor myInterceptor() {

        return new MyInterceptor();

    }

    @Override

    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(authorityInterceptor);

        registry.addInterceptor(myInterceptor()).addPathPatterns("/**")


                .excludePathPatterns("/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**","/config/**");

        super.addInterceptors(registry);

    }

    @Override

    protected void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("swagger-ui.html")

                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")

                .addResourceLocations("classpath:/META-INF/resources/webjars/");

    }

    @Bean

    public ErrorProperties errorProperties() {

        final ErrorProperties properties =new ErrorProperties();

        properties.setIncludeStacktrace(IncludeStacktrace.ALWAYS);

        return properties;

    }

    /**

     * 使用@Bean注解注入第三方的解析框架（fastJson）

     *

     * @return

     */

    @Bean

    public HttpMessageConverters fastJsonHttpMessageConverters() {

// 1、首先需要先定义一个convert转换消息对象

        FastJsonHttpMessageConverter fastConverter =new FastJsonHttpMessageConverter();

        // 2、添加fastJson的配置信息，比如：是否要格式化返回的json数据

        FastJsonConfig fastJsonConfig =new FastJsonConfig();

        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);

        // 3、在convert中添加配置信息

        fastConverter.setFastJsonConfig(fastJsonConfig);

        return new HttpMessageConverters(fastConverter);

    }

    @Bean(name ="multipartResolver")

    public MultipartResolver multipartResolver() {

        CommonsMultipartResolver resolver =new CommonsMultipartResolver();

        resolver.setDefaultEncoding("UTF-8");

        //resolveLazily属性启用是为了推迟文件解析，以在在UploadAction中捕获文件大小异常

        resolver.setResolveLazily(true);

        resolver.setMaxInMemorySize(40960);

        //上传文件大小 5M 5*1024*1024

        resolver.setMaxUploadSize(5 *1024 *1024);

        return resolver;

    }

}