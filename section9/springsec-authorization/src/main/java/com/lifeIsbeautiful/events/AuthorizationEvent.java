package com.lifeIsbeautiful.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.authorization.event.AuthorizationDeniedEvent;
import org.springframework.stereotype.Component;

@Component
public class AuthorizationEvent {

    private static final Logger logger = LoggerFactory.getLogger(AuthorizationEvent.class);

    @EventListener
    public void onFailure(AuthorizationDeniedEvent deniedEvent) {
        logger.warn("Login failed for the user: {} and the reason is {}",
                deniedEvent.getAuthentication().get().getName(),
                deniedEvent.getAuthorizationDecision().toString());
    }
}
