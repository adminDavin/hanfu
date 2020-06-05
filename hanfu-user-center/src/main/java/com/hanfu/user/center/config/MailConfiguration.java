//package com.hanfu.user.center.config;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.mail.javamail.JavaMailSenderImpl;
//
//@Configuration
//public class MailConfiguration {
//	
//	@Value("${spring.mail.smtp.host}")
//    private String host;
//	
//	@Value("${spring.mail.username}")
//	private String username;
//	
//	@Value("${spring.mail.password}")
//	private String password;
//	
//    @Bean
//    public JavaMailSenderImpl JavaMailSender(){
//    	JavaMailSender().send("123");
//        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();mailSender.send(mimeMessage);
//        mailSender.set
//        mailSender.setHost(host);
//        mailSender.setUsername(username);
//        mailSender.setPassword(password);
//        return  mailSender;
//    }
//}
