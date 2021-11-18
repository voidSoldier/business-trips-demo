package com.cmpn.tripsdemo.datasource;

import com.cmpn.tripsdemo.auth.CustomUserDetailsService;
import com.cmpn.tripsdemo.auth.TokenService;
import com.cmpn.tripsdemo.repos.TripMongoRepo;
import com.cmpn.tripsdemo.repos.UserMongoRepo;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class Controller {

    private UserMongoRepo userRepo;
    private TripMongoRepo tripRepo;
    private Producer producer;

    public Controller(UserMongoRepo userRepo, TripMongoRepo tripRepo, Producer producer) {
        this.userRepo = userRepo;
        this.tripRepo = tripRepo;
        this.producer = producer;
    }

    @GetMapping(path = "/users")
    public List<TokenService.User> getAllUsers() {
        List<TokenService.User> result = userRepo.findAll();
        result.add(getPrincipal());
        return result;
    }

    // понять, прошла ли авторизация
    private TokenService.User getPrincipal() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null && auth.getPrincipal() instanceof User) {
            User authenticated = (User) auth.getPrincipal();
            return new TokenService.User(
                    "some id",
                    authenticated.getUsername() + " -auth",
                    authenticated.getPassword(),
                    new HashSet(authenticated.getAuthorities()));
        } else
            return new TokenService.User("no auth", null);

    }
}
