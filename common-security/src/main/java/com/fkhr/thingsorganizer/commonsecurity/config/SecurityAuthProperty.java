package com.fkhr.thingsorganizer.commonsecurity.config;

import jakarta.validation.constraints.Pattern;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "security.auth")
public class SecurityAuthProperty {
    @Pattern(regexp = "keycloak|jwt|basic", message = "Allowed values: keycloak, jwt, basic")
    private String type = AuthType.KEYCLOAK.value;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
