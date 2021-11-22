package com.cmpn.tripsdemo;

import com.cmpn.tripsdemo.auth.TokenService;
import com.cmpn.tripsdemo.repos.TripMongoRepo;
import com.cmpn.tripsdemo.repos.UserMongoRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Arrays;

@SpringBootApplication(scanBasePackages = "com.cmpn.tripsdemo")
public class TripsDemoApplication implements CommandLineRunner {

    @Autowired
    private ApplicationContext appContext;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(TripsDemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
//        String passEncoded = passwordEncoder.encode("00000");
//        TokenService.User admin = new TokenService.User("admin", passEncoded, new SimpleGrantedAuthority("ROLE_ADMIN"));
//        TokenService.User user = new TokenService.User("user", passEncoded, new SimpleGrantedAuthority("ROLE_USER"));
//
//        UserMongoRepo userRepo = (UserMongoRepo) appContext.getBean("userMongoRepo");
//        userRepo.deleteAll();
//        userRepo.saveAll(Arrays.asList(admin, user));

//      TripMongoRepo tripRepo = (TripMongoRepo) appContext.getBean("tripMongoRepo");
//      tripRepo.deleteAll();
    }
}
