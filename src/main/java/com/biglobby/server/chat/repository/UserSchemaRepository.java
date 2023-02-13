package com.biglobby.server.chat.repository;
 
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.biglobby.server.chat.entity.UserSchema;

public interface UserSchemaRepository extends MongoRepository<UserSchema, String> {

    @Query("{'username': ?0}")
    public Optional<UserSchema> findByUsername(String username);
    
}
