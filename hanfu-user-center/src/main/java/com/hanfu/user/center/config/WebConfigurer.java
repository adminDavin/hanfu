package com.hanfu.user.center.config;


import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.hanfu.user.center.intercepter.AuthorityInterceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.ErrorProperties.IncludeStacktrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
@Configuration
public class WebConfigurer extends WebMvcConfigurationSupport {

    @Autowired
    private AuthorityInterceptor authorityInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authorityInterceptor)
                .addPathPatterns("/user/**")
                .excludePathPatterns("/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**");;
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
        final ErrorProperties properties = new ErrorProperties();
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
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        // 2、添加fastJson的配置信息，比如：是否要格式化返回的json数据
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
        // 3、在convert中添加配置信息
        fastConverter.setFastJsonConfig(fastJsonConfig);
        return new HttpMessageConverters(fastConverter);
    }


}
