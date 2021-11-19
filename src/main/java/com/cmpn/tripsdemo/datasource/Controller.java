package com.cmpn.tripsdemo.datasource;

import com.cmpn.tripsdemo.auth.TokenService;
import com.cmpn.tripsdemo.repos.UserMongoRepo;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class Controller {

  private UserMongoRepo userRepo;

  public Controller(UserMongoRepo userRepo) {
    this.userRepo = userRepo;
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
