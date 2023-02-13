package com.biglobby.server.chat.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.biglobby.server.chat.entity.ChatRoomSchema;
import com.biglobby.server.chat.entity.UserSchema;
import com.biglobby.server.chat.repository.ChatRoomSchemaRepository;
import com.biglobby.server.chat.repository.UserSchemaRepository;

@RestController

public class connection {

    @Autowired
    private UserSchemaRepository userSchemaRepo;

    @Autowired
    private ChatRoomSchemaRepository chatRoomRepo;

    @PostMapping("/biglobby/chat/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> data) {
        String username = data.get("username");
        Optional<UserSchema> userSchema = userSchemaRepo.findByUsername(username);
        Map<String, Object> res = new HashMap<String, Object>();
        List<ChatRoomSchema> rooms = chatRoomRepo.getByUsername(userSchema.get().getId());
        res.put("status", 200);
        res.put("userId", userSchema.get().getId());
        res.put("rooms", rooms);
        return ResponseEntity.ok(res);
    }
}
