package com.fkhr.thingsorganizer.commonsecurity.config;

import com.fkhr.thingsorganizer.commonsecurity.exceptionhandling.SecurityAccessDeniedHandler;
import com.fkhr.thingsorganizer.commonsecurity.exceptionhandling.SecurityAuthenticationEntryPoint;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
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
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@Import({com.fkhr.thingsorganizer.common.config.Config.class,
        SecurityAuthProperty.class
})
@ConditionalOnProperty(value = "security.auth.type", havingValue = "keycloak")
@AutoConfigureAfter(com.fkhr.thingsorganizer.common.config.Config.class)
public class SecurityKeycloakConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new KeycloakRoleConverter());
        http
                .sessionManagement(smc -> smc.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .requiresChannel(rcc -> rcc.anyRequest().requiresInsecure())//todo: change to secure after adding TLS certificates.
                .cors(corsConfig -> corsConfig.configurationSource(new CorsConfigurationSource() {
                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                        CorsConfiguration config = new CorsConfiguration();
                        config.setAllowedOrigins(List.of(SecurityConstant.ALLOWED_ORIGINS));
                        config.setAllowedMethods(Collections.singletonList("*"));
                        config.setAllowedHeaders(Collections.singletonList("*"));
                        config.setAllowCredentials(true);
                        config.setExposedHeaders(Arrays.asList(SecurityConstant.JWT_HEADER));
                        config.setMaxAge(3600l);
                        return config;
                    }
                }))
                .csrf(csrfConfig -> csrfConfig.disable())

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(SecurityConstant.PERMITTED_PAGES.toArray(new String[0])).permitAll()
                        .anyRequest().authenticated())
                .oauth2ResourceServer(rsc ->
                        rsc.jwt(jwtConfigurer ->
                                jwtConfigurer.jwtAuthenticationConverter(jwtAuthenticationConverter)))
                .exceptionHandling(ehc -> ehc.accessDeniedHandler(new SecurityAccessDeniedHandler()));

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
