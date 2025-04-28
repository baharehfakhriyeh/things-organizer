package com.fkhr.thingsorganizer.commonsecurity.filter;

import com.fkhr.thingsorganizer.commonsecurity.config.SecurityConstant;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class JwtTokeValidatorFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = request.getHeader(SecurityConstant.JWT_HEADER);
            if (jwt != null) {

                if (jwt.contains("Bearer ")) {
                    jwt = jwt.split("Bearer ")[1];
                }
                Environment environment = getEnvironment();
                if (environment != null) {
                    String secret = environment.getProperty(SecurityConstant.JWT_SECRET_KEY, SecurityConstant.JWT_SECRET_DEFAULT_VALUE);
                    SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
                    if (secretKey != null) {
                        Claims claims = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(jwt).getPayload();
                        String username = String.valueOf(claims.get(SecurityConstant.JWT_CLAIM_USERNAME_KEY));
                        String authorities = String.valueOf(claims.get(SecurityConstant.JWT_CLAIM_AUTHORITIES_KEY));
                        Authentication authentication = new UsernamePasswordAuthenticationToken(username, null,
                                AuthorityUtils.commaSeparatedStringToAuthorityList(authorities));
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }

            }
        } catch (Exception exception) {
            throw new BadCredentialsException("Invalid token received!");
        }
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        //return request.getServletPath().(SecurityConstant.JWT_IGNORE_VALIDATION_URIS);
        return SecurityConstant.JWT_IGNORE_VALIDATION_URIS.contains(request.getServletPath());
    }
}
