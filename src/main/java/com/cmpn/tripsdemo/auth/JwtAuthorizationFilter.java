package com.cmpn.tripsdemo.auth;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Deprecated
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private TokenService tokenService;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, TokenService tokenService) {
        super(authenticationManager);
        this.tokenService = tokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

//        String authHeader = request.getHeader("auth-token");
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (isInvalid(authHeader)) {
            filterChain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken token = getToken(authHeader);
        SecurityContextHolder.getContext().setAuthentication(token);
        filterChain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getToken(String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        TokenService.User userPrincipal = tokenService.parseToken(token);

        List<GrantedAuthority> authorities = new ArrayList<>(userPrincipal.getAuthorities());
        return new UsernamePasswordAuthenticationToken(userPrincipal, userPrincipal.getPassword(), authorities);
    }

    private boolean isInvalid(String authHeader) {
        return authHeader == null ||
                authHeader.isBlank() ||
                !authHeader.startsWith("Bearer ");
    }
}
