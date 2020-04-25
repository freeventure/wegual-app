package com.wegual.beneficiaryservice;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import app.wegual.common.config.SecurityProperties;

@Component
@Configuration
@ConfigurationProperties(prefix = "rest.security")
public class BeneficiarySecurityProperties extends SecurityProperties {

}
