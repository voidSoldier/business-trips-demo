package com.cmpn.tripsdemo.auth;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Deprecated
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private TokenService tokenService;
    private AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getParameter("username"), request.getParameter("password"))
        );
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
//        HashMap<String, Object> responseBody = new HashMap<>();
        String token = tokenService.generateToken((User) authResult.getPrincipal());

//        responseBody.put("auth-token", token);
//        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//        new ObjectMapper().writeValue(response.getWriter(), responseBody);
//        response.addHeader("auth-token", token);
        response.addHeader(HttpHeaders.AUTHORIZATION, token);
    }
}
