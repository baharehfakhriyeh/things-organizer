package com.fkhr.thingsorganizer.commonsecurity.filter;

import com.fkhr.thingsorganizer.commonsecurity.config.SecurityConstant;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.stream.Collectors;

public class JwtTokenGeneratorFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            Environment environment = getEnvironment();
            if (environment != null) {
                String secret = environment.getProperty(SecurityConstant.JWT_SECRET_KEY, SecurityConstant.JWT_SECRET_DEFAULT_VALUE);
                SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
                String jwt = Jwts.builder().issuer(SecurityConstant.JWT_ISSUER).subject(SecurityConstant.JWT_SUBJECT)
                        .claim(SecurityConstant.JWT_CLAIM_USERNAME_KEY, authentication.getName())
                        .claim(SecurityConstant.JWT_CLAIM_AUTHORITIES_KEY, authentication.getAuthorities().stream().map(
                                GrantedAuthority::getAuthority
                        ).collect(Collectors.joining(",")))
                        .issuedAt(new Date())
                        .expiration(new Date((new Date()).getTime() + SecurityConstant.JWT_EXPIRATION_DURATION))
                        .signWith(secretKey).compact();
                response.setHeader(SecurityConstant.JWT_HEADER, jwt);
            }
        }
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return !SecurityConstant.JWT_GENERATOR_URI.contains(request.getServletPath());
    }
}
