package com.biglobby.server.chat.controller;


import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.biglobby.server.chat.entity.ChatRoomSchema;
import com.biglobby.server.chat.entity.MessageSchema;
import com.biglobby.server.chat.repository.ChatRoomSchemaRepository;
import com.biglobby.server.chat.repository.MessageSchemaRepository;

@RestController
@CrossOrigin("*")
public class MessageSchemaController {

    @Autowired
    private MessageSchemaRepository messageRepo;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    
    @Autowired
    private ChatRoomSchemaRepository chatRoomRepo;
    	
    @GetMapping(value = {"/chat/{roomId}/conversation"}, params = {"page", "limit"})
    public ResponseEntity<Page<MessageSchema>> getConversationByRoomId(@PathVariable("roomId") String roomId,
            @RequestParam("page") Integer page, @RequestParam("limit") Integer limit) {
        Pageable pageable = PageRequest.of(page, limit, Sort.by("createAt").descending());
        return ResponseEntity.ok(messageRepo.getByRoomId(roomId, pageable));
    }

    @MessageMapping("/chat/room")
    public void sendMessage(MessageSchema data) { 
        MessageSchema message = messageRepo.save(data);
        ChatRoomSchema room = chatRoomRepo.findById(data.getRoomId()).orElse(null);
        if(room != null) {
        	room.setUpdateAt(new Date());
        	chatRoomRepo.save(room);
        }
        simpMessagingTemplate.convertAndSend("/chat/room/" + message.getRoomId(), message);
    }
}
