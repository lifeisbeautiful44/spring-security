package com.lifeIsbeautiful.filter;

import com.lifeIsbeautiful.constant.ApplicationConstant;
import io.jsonwebtoken.JwtBuilder;
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

public class JWTTokenGeneratorFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            Environment env = getEnvironment();
            if (env != null) {
                String secret = env.getProperty(ApplicationConstant.JWT_SECRET_KEY,
                        ApplicationConstant.JWT_DEFAULT_VALUE);

                SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

                String jwtToken = Jwts.builder().issuer("Lhng bank").subject("JWT token")
                        .claim("username", authentication.getPrincipal())
                        .claim("authorities", authentication.getAuthorities().stream()
                                .map(GrantedAuthority::getAuthority).collect(Collectors.joining(",")))
                        .issuedAt(new Date())
                        .expiration(new Date((new Date().getTime() + 300000)))
                        .signWith(secretKey)
                        .compact();

                response.setHeader(ApplicationConstant.JWT_HEADER, jwtToken);
            }
        }
        filterChain.doFilter(request,response);
    }

    //Note: If true then skip this filter.
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return !request.getServletPath().equalsIgnoreCase("/user");
    }

}
