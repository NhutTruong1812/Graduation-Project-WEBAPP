package com.biglobby.server.chat.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.biglobby.server.chat.entity.ChatRoomSchema;
import com.biglobby.server.chat.service.ChatRoomSchemaService;

@RestController
@CrossOrigin
@RequestMapping("/api/chatrooms")
public class ChatRoomRestController {

    @Autowired
    private ChatRoomSchemaService roomService;

    @GetMapping
    public ResponseEntity<List<ChatRoomSchema>> getAll() {
        return roomService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChatRoomSchema> getById(@PathVariable("id") String id) {
        return roomService.getById(id);
    }

    @GetMapping(params = { "page", "limit", "user.username" })
    public ResponseEntity<Page<ChatRoomSchema>> getPageByUsername(@RequestParam("page") Integer pagenum,
            @RequestParam("limit") Integer limit, @RequestParam("user.username") String username) {
        return roomService.getPageByUsername(pagenum - 1, limit, username);
    }

    @PostMapping
    public ResponseEntity<ChatRoomSchema> addRoom(@RequestBody ChatRoomSchema room) {
        return roomService.add(room);
    }

}
