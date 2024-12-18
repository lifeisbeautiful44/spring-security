package com.lifeIsbeautiful.filter;

import jakarta.servlet.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;

public class AuthoritiesLoggingFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(AuthoritiesLoggingFilter.class);


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            logger.info("User logged in is {} with roles: {}", authentication.getName(), authentication.getAuthorities());
        }
        chain.doFilter(request,response);
    }
}
