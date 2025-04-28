package com.fkhr.thingsorganizer.commonsecurity.config;

import com.fkhr.thingsorganizer.commonsecurity.exceptionhandling.SecurityAccessDeniedHandler;
import com.fkhr.thingsorganizer.commonsecurity.exceptionhandling.SecurityAuthenticationEntryPoint;
import com.fkhr.thingsorganizer.commonsecurity.filter.CsrfCookieFilter;
import com.fkhr.thingsorganizer.commonsecurity.filter.JwtTokeValidatorFilter;
import com.fkhr.thingsorganizer.commonsecurity.filter.JwtTokenGeneratorFilter;
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
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * This config is used only for UI and not stateless situation.
 * The csrf is enabled here with basic auth and no jwt.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@ConditionalOnProperty(value = "security.auth.type", havingValue = "basic")
@AutoConfigureAfter(com.fkhr.thingsorganizer.common.config.Config.class)
public class SecurityBasicConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {


        //not used in stateless mode.
        CsrfTokenRequestAttributeHandler csrfTokenRequestAttributeHandler = new CsrfTokenRequestAttributeHandler();


        http
                .sessionManagement(smc -> smc.sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                        /*.maximumSessions(3).maxSessionsPreventsLogin(true)*/)
                .requiresChannel(rcc -> rcc.anyRequest().requiresInsecure())//todo: change to secure after adding TLS certificates.
                .cors(corsConfig -> corsConfig.configurationSource(new CorsConfigurationSource() {
                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                        CorsConfiguration config = new CorsConfiguration();
                        config.setAllowedOrigins(List.of(SecurityConstant.ALLOWED_ORIGINS));
                        config.setAllowedMethods(Collections.singletonList("*"));
                        config.setAllowedHeaders(Collections.singletonList("*"));
                        config.setAllowCredentials(true);
                        config.setMaxAge(3600l);
                        return config;
                    }
                }))

                //not used in stateless mode.
                .csrf(csrfConfig -> csrfConfig.csrfTokenRequestHandler(csrfTokenRequestAttributeHandler)
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                //not used in stateless mode
                .addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(SecurityConstant.PERMITTED_PAGES.toArray(new String[0])).permitAll()
                        .anyRequest().authenticated())
                .httpBasic(hbc -> hbc.authenticationEntryPoint(new SecurityAuthenticationEntryPoint()))
                .exceptionHandling(ehc -> ehc.accessDeniedHandler(new SecurityAccessDeniedHandler()));
        return http.build();
    }


    /**
     * InMemory User data, just for learning
     * @return
     */
    /*@Bean
    public UserDetailsService userDetailsService(){
        //website : bcrypt-generator.com can generate hashcode of the value.
        UserDetails user = User.withUsername("user").password("$2a$12$bxqiZn7T8V7WWivJyEbnhOE25Aozl/GTsIm0FFbgfB99m1J2VYdi6").authorities("admin").build();//12345
        UserDetails admin = User.withUsername("admin").password("$2a$12$/8ANYdzr3DWuUQoPXgAUyeedyPi8b/Tva5babE3C5G7MraRLf6Cu.").authorities("admin").build();//BaharFakhr@12345
        return new InMemoryUserDetailsManager(user, admin);
    }*/

   /*
   //just for learning
    @Bean
    public UserDetailsService userDetailsService(DataSource dataSource){
        return new JdbcUserDetailsManager(dataSource);
    }*/

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

   /* @Bean
    public CompromisedPasswordChecker compromisedPasswordChecker(){
        return new HaveIBeenPwnedRestApiPasswordChecker();
    }*/



}
