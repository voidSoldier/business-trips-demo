package com.cmpn.tripsdemo.datasource;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    private UserDetailsService userDetailsService;

    public UserController(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @PostMapping(path = "login")
    public ResponseEntity<?> auth(@RequestParam String username, @RequestParam String password,
                                  HttpServletResponse resp) {
      //  User u = new User(username, password, Collections.emptySet());
        String token = "Bearer auth-token";
        resp.addHeader(HttpHeaders.AUTHORIZATION, token);

        User principal = (User) userDetailsService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);

        return ResponseEntity.ok(token);
    }
}
