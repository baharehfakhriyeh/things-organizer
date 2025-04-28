package com.fkhr.thingsorganizer.commonsecurity.service.event;

import com.fkhr.thingsorganizer.common.logging.LoggingAspect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationEventListener {
    private static final Logger logger = LogManager.getLogger(LoggingAspect.class);

    @EventListener
    public void onSuccess(AuthenticationSuccessEvent successEvent){
        logger.info("Successful login for the user: {}", successEvent.getAuthentication().getName());
    }

    @EventListener
    public void onFailure(AbstractAuthenticationFailureEvent failureEvent){
        logger.info("Login failure for the user: {} due to: {}",
                failureEvent.getAuthentication().getName(), failureEvent.getException().getMessage());
    }
}
