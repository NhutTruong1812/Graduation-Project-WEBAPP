package com.biglobby.server.chat.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.biglobby.server.chat.entity.UserSchema;
import com.biglobby.server.chat.repository.UserSchemaRepository; 

@RestController
@CrossOrigin("*")
public class UserSchemaController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private UserSchemaRepository userRepo;

    @PostMapping("/biglobby/chat/register")
    public ResponseEntity<UserSchema> register(@RequestBody UserSchema user) {
        UserSchema added = userRepo.save(user);
        if (added != null) {
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        // Map<String, String> data = new HashMap<>();
        // data.put("message", "success");
        // simpMessagingTemplate.convertAndSend("/chat/registerResponse", data);

    }

    @GetMapping("/biglobby/chat/users")
    public ResponseEntity<List<UserSchema>> getUsers() {
        // System.out.println("Handling get all user.....");
        // List<UserSchema> users = userRepo.findAll();
        return ResponseEntity.ok(userRepo.findAll());
        // simpMessagingTemplate.convertAndSend("/chat/getUsersResponse", users);
    }

    @MessageMapping("/chat/users/{userid}")
    public void getUserById(@DestinationVariable String userId) {
        Optional<UserSchema> user = userRepo.findById(userId);
        Map<String, Object> res = new HashMap<String, Object>();
        if (!user.isPresent()) {
            res.put("message", "not found");
            res.put("status", "404");
        } else {
            res.put("message", "success");
            res.put("user", user.get());
        }
        simpMessagingTemplate.convertAndSend("/chat/getUserByIdResponse", res);

    }

}
