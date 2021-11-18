package com.cmpn.tripsdemo.config;

import com.cmpn.tripsdemo.auth.CustomUserDetailsService;
import com.cmpn.tripsdemo.auth.OncePerRequestAuthFilter;
import com.cmpn.tripsdemo.auth.TokenService;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;
    private final CustomUserDetailsService userDetailsService;

    public WebSecurityConfig(TokenService tokenService, PasswordEncoder passwordEncoder, CustomUserDetailsService userDetailsService) {
        this.tokenService = tokenService;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
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
//                .antMatcher("/api/**").authorizeRequests()
//                .anyRequest().authenticated()
//                .and()
                .addFilterBefore(new OncePerRequestAuthFilter(tokenService, userDetailsService()),
                        UsernamePasswordAuthenticationFilter.class);
    }
}
