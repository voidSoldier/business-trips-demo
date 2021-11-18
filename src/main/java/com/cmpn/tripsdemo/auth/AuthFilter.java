package com.cmpn.tripsdemo.auth;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Deprecated
public class AuthFilter extends BasicAuthenticationFilter {

    private TokenService tokenService;
    private UserDetailsService userDetailsService;
    private static final String LOGIN_URL = "http://localhost:8080/auth/login";

    public AuthFilter(AuthenticationManager authenticationManager, TokenService tokenService, UserDetailsService userDetailsService) {
        super(authenticationManager);
        this.tokenService = tokenService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (!isInvalid(authHeader) && tokenService.verifyToken(authHeader)
        ) {
//        || LOGIN_URL.equals(request.getRequestURL().toString())) {
            String username = request.getParameter("username");
            authenticate(username);
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

    private void authenticate(String username) {
        User principal = (User) userDetailsService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(token);
    }
}
