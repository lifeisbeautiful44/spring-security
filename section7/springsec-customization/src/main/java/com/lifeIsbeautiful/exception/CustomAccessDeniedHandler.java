package com.lifeIsbeautiful.exception;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;
import java.time.LocalDateTime;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
        // Build the JSON response
        LocalDateTime dateTime = LocalDateTime.now();
        String status = String.valueOf(HttpStatus.FORBIDDEN.value());
        String message = (accessDeniedException != null && accessDeniedException.getMessage() != null) ? accessDeniedException.getMessage() : "Authorization Failed";
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
        response.setHeader("lhng-error-message", "Authorization Failed");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");
        response.getWriter().write(jsonResponse);

    }
}
