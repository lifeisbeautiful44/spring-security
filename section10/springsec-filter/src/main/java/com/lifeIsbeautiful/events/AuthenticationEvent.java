package com.lifeIsbeautiful.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationEvent {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationEvent.class);

    @EventListener
    public void onSuccess(AuthenticationSuccessEvent successEvent) {
        logger.info("Successful login for the user: {}", successEvent.getAuthentication().getName());
    }

    @EventListener
    public void onFailure(AbstractAuthenticationFailureEvent failureEvent) {
        logger.warn("Login failed for the user: {} and the reason is {}",
                failureEvent.getAuthentication().getName(),
                failureEvent.getException().getMessage());
    }
}
