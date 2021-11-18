package com.cmpn.tripsdemo.repos;

import com.cmpn.tripsdemo.auth.TokenService;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;


public interface UserMongoRepo extends MongoRepository<TokenService.User, String> {

public Optional<TokenService.User> findUserByUsername(String username);

}

