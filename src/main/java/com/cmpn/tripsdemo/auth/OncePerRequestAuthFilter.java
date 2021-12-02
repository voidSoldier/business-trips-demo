package com.cmpn.tripsdemo.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class OncePerRequestAuthFilter extends OncePerRequestFilter {

  private final TokenService tokenService;

  private final String bearer;

  public OncePerRequestAuthFilter(TokenService tokenService, @Value("${token.bearer}") String bearer) {
    this.tokenService = tokenService;
    this.bearer = bearer;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

    if (!isInvalid(authHeader) && tokenService.verifyToken(authHeader)) {
      filterChain.doFilter(request, response);
    } else
      throw new ServletException("no access");
  }

  private boolean isInvalid(String authHeader) {
    return authHeader == null ||
      authHeader.isBlank() ||
      !authHeader.startsWith(bearer);
  }
}
