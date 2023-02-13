package com.biglobby.server.chat.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.biglobby.server.chat.entity.MessageSchema;
import com.biglobby.server.chat.service.MessageSchemaService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@CrossOrigin
@RequestMapping("/api/messages")
public class MessageRestController {

    @Autowired
    private MessageSchemaService messageService;

    @GetMapping
    public ResponseEntity<List<MessageSchema>> getAll() {
        return messageService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MessageSchema> getById(@PathVariable("id") String id) {
        return messageService.getById(id);
    }

    @GetMapping(params = { "page", "limit", "room.id" })
    public ResponseEntity<Page<MessageSchema>> getPageByRoomId(@RequestParam("page") Integer pagenum,
            @RequestParam("limit") Integer limit, @RequestParam("room.id") String roomId) {
        return messageService.getPageByRoomId(roomId, pagenum-1, limit);
    }
}
