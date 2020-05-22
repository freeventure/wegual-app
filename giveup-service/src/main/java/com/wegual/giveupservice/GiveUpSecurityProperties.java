package com.wegual.giveupservice;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import app.wegual.common.config.SecurityProperties;

@Component
@Configuration
@ConfigurationProperties(prefix = "rest.security")
public class GiveUpSecurityProperties extends SecurityProperties{

}
