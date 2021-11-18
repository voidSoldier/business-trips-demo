package com.cmpn.tripsdemo.auth;

import com.cmpn.tripsdemo.repos.UserMongoRepo;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UserMongoRepo userMongoRepo;

    public CustomUserDetailsService(UserMongoRepo userMongoRepo) {
        this.userMongoRepo = userMongoRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userMongoRepo.findUserByUsername(username)
                .map(user -> new User(
                        user.getUsername(),
                        user.getPassword(),
                        user.getAuthorities()))
                .orElseThrow(() -> new UsernameNotFoundException(
                        "User with username " + username + " doesn't exist!"));
    }
}
