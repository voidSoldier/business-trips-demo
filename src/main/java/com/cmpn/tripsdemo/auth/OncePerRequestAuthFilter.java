package com.cmpn.tripsdemo.auth;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class OncePerRequestAuthFilter extends OncePerRequestFilter {

    private final TokenService tokenService;

    public OncePerRequestAuthFilter(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (!isInvalid(authHeader) && tokenService.verifyToken(authHeader)) {
            filterChain.doFilter(request, response);
        }
        else
            throw new ServletException("no access");
    }

    private boolean isInvalid(String authHeader) {
        return authHeader == null ||
                authHeader.isBlank() ||
                !authHeader.startsWith("Bearer ");
    }
}
