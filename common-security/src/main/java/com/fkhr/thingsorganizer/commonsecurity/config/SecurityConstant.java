package com.fkhr.thingsorganizer.commonsecurity.config;

import org.springframework.beans.factory.annotation.Value;

import java.util.Arrays;
import java.util.List;

public final class SecurityConstant {
    public static final String JWT_SECRET_KEY = "THO_SECRET";
    public static final String JWT_SECRET_DEFAULT_VALUE = "hdkis3lrje0olmfo1tqc8dhicjfoeu3lsfe92kmgq7dckspwekdb7slrlwsdo";
    public static final String JWT_HEADER = "Authorization";
    public static final String JWT_ISSUER = "THINGS_ORGANIZER";
    public static final String JWT_SUBJECT = "JWT_Token";
    public static final String JWT_CLAIM_USERNAME_KEY= "username";
    public static final String JWT_CLAIM_AUTHORITIES_KEY = "authorities";
    public static final int JWT_EXPIRATION_DURATION = 600000; //ms
    public static final List<String> JWT_GENERATOR_URI = Arrays.asList(
            "/users/login"
    );

    public static final List<String> PERMITTED_PAGES = Arrays.asList(
            "/api-docs",
            "/security/api-docs",
            "/thing/api-docs",
            "/content/api-docs",
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/users/sign-up",
            "/actuator/prometheus"
    );

    public static final List<String> JWT_IGNORE_VALIDATION_URIS = Arrays.asList(
            "/api-docs",
            "/security/api-docs",
            "/thing/api-docs",
            "/content/api-docs",
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/users/login",
            "/users/sign-up",
            "/actuator/prometheus"
    );

}
