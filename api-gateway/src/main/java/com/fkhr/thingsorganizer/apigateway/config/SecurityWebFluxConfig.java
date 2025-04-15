package com.fkhr.thingsorganizer.apigateway.config;

/* //todo: uncomment when added auth to this module.
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
@Import({com.fkhr.thingsorganizer.common.config.Config.class})*/
public class SecurityWebFluxConfig {
    /*@Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        String[] WHITELIST_PAGES = new String[]{"/api-docs",
                "/swagger-ui.html",
                "/swagger-ui/**",
                "/users/password",
                "/users/sign-up"
        };
        http
                .csrf(scc->scc.disable())
*//*
.sessionManagement(session -> session
                        .sessionCreationPolicy(org.springframework.security.config.http.SessionCreationPolicy.STATELESS))
*//*


                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers(WHITELIST_PAGES).permitAll()
                        .anyExchange().authenticated()
                )
                .httpBasic();

        return http.build();
    }*/

    /*@Bean
    public MapReactiveUserDetailsService userDetailsService() {
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("user")
                .password("user")
                .roles("USER")
                .build();
        return new MapReactiveUserDetailsService(user);
    }*/

/* @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((requests) -> requests.anyRequest().permitAll());
               // .requestMatchers("/login").authenticated()
               // .requestMatchers("/security/login").permitAll());

        //http.formLogin(withDefaults());
        http.formLogin(flc -> flc.disable());
        //http.httpBasic(withDefaults());
        http.httpBasic(hbc->hbc.disable());
        return http.build();
    }*/




/*@Bean
    public UserDetailsService userDetailsService(){
        //website : bcrypt-generator.com can generate hashcode of the value.
        UserDetails user = User.withUsername("user").password("$2a$12$bxqiZn7T8V7WWivJyEbnhOE25Aozl/GTsIm0FFbgfB99m1J2VYdi6").authorities("admin").build();//12345
        UserDetails admin = User.withUsername("admin").password("$2a$12$/8ANYdzr3DWuUQoPXgAUyeedyPi8b/Tva5babE3C5G7MraRLf6Cu.").authorities("admin").build();//BaharFakhr@12345
        return new InMemoryUserDetailsManager(user, admin);
    }*/


 /*@Bean
    public UserDetailsService userDetailsService(DataSource dataSource){
        return new JdbcUserDetailsManager(dataSource);
    }*/


   /* @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
*/
 /*@Bean
    public CompromisedPasswordChecker compromisedPasswordChecker(){
        return new HaveIBeenPwnedRestApiPasswordChecker();
    }*/




}
