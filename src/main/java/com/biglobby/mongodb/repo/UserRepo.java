package com.biglobby.mongodb.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.biglobby.mongodb.document.User;

public interface UserRepo extends MongoRepository<User, String>{

    @Query("{username: '?0'}")
    User findItemByUsername(String username);
}
