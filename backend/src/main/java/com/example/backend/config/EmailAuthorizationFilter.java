package com.example.backend.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import java.io.IOException;

public class EmailAuthorizationFilter extends AbstractPreAuthenticatedProcessingFilter {

    @Override
    protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
        System.out.println("in getPreAuthenticatedPrincipal() ");
        // Extract the JWT from the Security Context
        System.out.println(" SecurityContextHolder = " + SecurityContextHolder.getContext());
        System.out.println(" SecurityContextHolder.getContext().getAuthentication() = "
                +  SecurityContextHolder.getContext().getAuthentication());
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Extract email from JWT
        String email = jwt.getClaim("email");
        System.out.println("email = " + email);

        // Authorize based on the email
        if ("some.one@some.email".equals(email)) {
            return email;  // Continue the request
        } else {
            throw new AccessDeniedException("Access denied for email: " + email);
        }
    }

    @Override
    protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
        return null;
    }

//    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        super.doFilter(request, response, chain);
    }
}

