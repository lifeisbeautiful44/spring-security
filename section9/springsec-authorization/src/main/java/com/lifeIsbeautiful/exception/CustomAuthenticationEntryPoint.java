package com.lifeIsbeautiful.exception;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        // Build the JSON response
        LocalDateTime dateTime = LocalDateTime.now();
        String status = String.valueOf(HttpStatus.UNAUTHORIZED.value());
        String message = (authException != null && authException.getMessage() != null) ? authException.getMessage() : "Authentication Failed";
        String path = request.getRequestURI();

        String jsonResponse = String.format(
                "{\n" +
                        "    \"timestamp\": \"%s\",\n" +
                        "    \"status\": %s,\n" +
                        "    \"error\": \"%s\",\n" +
                        "    \"path\": \"%s\"\n" +
                        "}",
                dateTime, status, message, path
        );

        // Set response headers and body
        response.setHeader("lhng-error-message", "Authentication Failed");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write(jsonResponse);

    }
}
