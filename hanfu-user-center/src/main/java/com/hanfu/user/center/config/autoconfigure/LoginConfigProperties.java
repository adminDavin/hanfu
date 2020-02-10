package com.hanfu.user.center.config.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = LoginConfigProperties.LOGIN_PREFIX)
public class LoginConfigProperties {

    public static final String LOGIN_PREFIX = "login";

}
