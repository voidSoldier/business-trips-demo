package com.cmpn.tripsdemo.config;

import com.cmpn.tripsdemo.auth.CustomUserDetailsService;
import com.cmpn.tripsdemo.auth.OncePerRequestAuthFilter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  private final PasswordEncoder passwordEncoder;

  private final CustomUserDetailsService userDetailsService;

  private final OncePerRequestAuthFilter oncePerRequestAuthFilter;

  public WebSecurityConfig(PasswordEncoder passwordEncoder,
                           CustomUserDetailsService userDetailsService, OncePerRequestAuthFilter oncePerRequestAuthFilter) {
    this.passwordEncoder = passwordEncoder;
    this.userDetailsService = userDetailsService;
    this.oncePerRequestAuthFilter = oncePerRequestAuthFilter;
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
      .csrf().disable()
      .exceptionHandling()
      .and()
      .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      .and()
      .addFilterBefore(oncePerRequestAuthFilter,
        UsernamePasswordAuthenticationFilter.class);
  }
}
