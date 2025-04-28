package com.fkhr.thingsorganizer.commonsecurity.config;

import com.fkhr.thingsorganizer.commonsecurity.exceptionhandling.SecurityAccessDeniedHandler;
import com.fkhr.thingsorganizer.commonsecurity.exceptionhandling.SecurityAuthenticationEntryPoint;
import com.fkhr.thingsorganizer.commonsecurity.filter.JwtTokeValidatorFilter;
import com.fkhr.thingsorganizer.commonsecurity.filter.JwtTokenGeneratorFilter;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
@Import({com.fkhr.thingsorganizer.common.config.Config.class,
        SecurityKeycloakConfig.class, SecurityJwtConfig.class, SecurityBasicConfig.class
})
public class SecurityConfig {

}
