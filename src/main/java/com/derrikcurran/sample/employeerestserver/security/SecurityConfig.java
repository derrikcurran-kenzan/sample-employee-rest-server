package com.derrikcurran.sample.employeerestserver.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.convert.DurationUnit;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

@Configuration
@ConfigurationProperties(prefix = "security")
public class SecurityConfig {

    private String tokenSecret;

    @DurationUnit(ChronoUnit.SECONDS)
    private Duration tokenExpiration;

    private String tokenPrefix;
    private String authHeaderKey;

    public String getTokenSecret() {
        return tokenSecret;
    }

    public void setTokenSecret(String tokenSecret) {
        this.tokenSecret = tokenSecret;
    }

    public Duration getTokenExpiration() {
        return tokenExpiration;
    }

    public void setTokenExpiration(Duration tokenExpiration) {
        this.tokenExpiration = tokenExpiration;
    }

    public String getTokenPrefix() {
        return tokenPrefix;
    }

    public void setTokenPrefix(String tokenPrefix) {
        this.tokenPrefix = tokenPrefix;
    }

    public String getAuthHeaderKey() {
        return authHeaderKey;
    }

    public void setAuthHeaderKey(String authHeaderKey) {
        this.authHeaderKey = authHeaderKey;
    }
}
