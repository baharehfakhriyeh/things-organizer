package com.fkhr.thingsorganizer.commonsecurity.config;

import com.fkhr.thingsorganizer.commonsecurity.exceptionhandling.SecurityAccessDeniedHandler;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
/*@Import({com.fkhr.thingsorganizer.common.config.Config.class,
        SecurityProperty.class
})*/

@ConditionalOnProperty(value = "security.authtype", havingValue = "keycloak")
@AutoConfigureAfter(SecurityProperty.class)
public class SecurityKeycloakConfig {
    private final SecurityProperty securityProperty;

    public SecurityKeycloakConfig(SecurityProperty securityProperty) {
        this.securityProperty = securityProperty;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new KeycloakRoleConverter());
        http
                //.cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers("/ws/**").permitAll() //todo: secure it in proper way
                        .requestMatchers(SecurityConstant.PERMITTED_PAGES.toArray(new String[0])).permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(smc -> smc.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .oauth2ResourceServer(rsc -> rsc.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter)))
                .exceptionHandling(ehc -> ehc.accessDeniedHandler(new SecurityAccessDeniedHandler()));

        return http.build();
    }



    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
