package com.fkhr.thingsorganizer.commonsecurity.exceptionhandling;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;
import java.time.LocalDateTime;

public class SecurityAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        String message = (authException != null && authException.getMessage() != null) ?
                authException.getMessage() : "Unauthorized";
        LocalDateTime currentTime = LocalDateTime.now();
        response.setHeader("error-reason", "Authentication failed.");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json;charset=UTF-8");

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode jsonError = objectMapper.createObjectNode();
        jsonError.put("code", HttpStatus.UNAUTHORIZED.value());
        jsonError.put("error", HttpStatus.UNAUTHORIZED.getReasonPhrase());
        jsonError.put("cause", message);
        jsonError.put("time", currentTime.toString());
        response.getWriter().write(jsonError.toString());
    }
}
