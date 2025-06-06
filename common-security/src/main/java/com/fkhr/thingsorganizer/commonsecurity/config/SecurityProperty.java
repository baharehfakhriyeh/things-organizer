package com.fkhr.thingsorganizer.commonsecurity.config;

import jakarta.validation.constraints.Pattern;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "security")
@AutoConfigureAfter(com.fkhr.thingsorganizer.common.config.Config.class)
public class SecurityProperty {
    @Pattern(regexp = "keycloak|jwt|basic", message = "Allowed values: keycloak, jwt, basic")
    private String authtype = AuthType.KEYCLOAK.value;
    private String[] allowedorigins;

    public String getAuthtype() {
        return authtype;
    }

    public void setAuthtype(String authtype) {
        this.authtype = authtype;
    }
    public String[] getAllowedorigins() {
        return allowedorigins;
    }

    public void setAllowedorigins(String[] allowedorigins) {
        this.allowedorigins = allowedorigins;
    }

    public enum AuthType {
        KEYCLOAK("keycloak"),
        JWT("jwt"),
        BASIC("basic");

        private final String value;
        public static final String KEYCLOAK_VALUE = KEYCLOAK.value;
        public static final String JWT_VALUE = JWT.value;
        public static final String BASIC_VALUE = BASIC.value;

        AuthType(String value){
            this.value = value.toLowerCase();
        }

    }
}
