package com.biglobby.mongodb.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.biglobby.mongodb.document.User;
import com.biglobby.mongodb.repo.UserRepo; 

@RestController
@CrossOrigin("*")
@RequestMapping("/biglobby/mongo/users")
public class UserRest {
    @Autowired
    UserRepo userRepo;
    @GetMapping
    public ResponseEntity<List<User>> get() { 
      return ResponseEntity.ok(userRepo.findAll());
    }
    @PostMapping
    public ResponseEntity<User> add(@RequestBody User user){
      User added = userRepo.save(user);
      return ResponseEntity.ok(added);
    }
}
