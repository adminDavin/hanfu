//package com.hanfu.cancel.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
//
//
//    @Autowired
//    private UserDetailsService userDetailsService;
//
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//
//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        super.configure(web);
//    }
//
//    @Override
//    public void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
////                .passwordEncoder(passwordEncoder());//passwoldEncoder是对密码的加密处理，如果user中密码没有加密，则可以不加此方法。注意加密请使用security自带的加密方式。
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable()//禁用了 csrf 功能
//                .authorizeRequests()//限定签名成功的请求
//                .antMatchers("/decision/**","/order/**","/employee/*","/cancel/**","/activeIndex/**").hasAnyRole("EMPLOYEE","ADMIN")//对decision和govern 下的接口 需要 USER 或者 ADMIN 权限
//                .antMatchers("/employee/login").permitAll()///employee/login 不限定
//                .antMatchers("/admin/**").hasRole("ADMIN")//对admin下的接口 需要ADMIN权限
//                .antMatchers("/oauth/**","/employee/login").permitAll()//不拦截 oauth 开放的资源
//                .anyRequest().permitAll()//其他没有限定的请求，允许访问
//                .and().anonymous()//对于没有配置权限的其他请求允许匿名访问
//                .and().formLogin()//使用 spring security 默认登录页面
//                .loginPage("http://192.168.1.125:9901/Demo/demo")
//                .usernameParameter("authKey")	//自定义用户名请求参数名
//                .passwordParameter("passwd")	//自定义密码请求参数名
//                .loginProcessingUrl("/employee/greeting")
//                .and().httpBasic();//启用http 基础验证
//
//    }
//
//}