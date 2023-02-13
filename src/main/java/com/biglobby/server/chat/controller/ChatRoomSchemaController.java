package com.biglobby.server.chat.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.biglobby.server.chat.entity.ChatRoomSchema;
import com.biglobby.server.chat.repository.ChatRoomSchemaRepository;

@Controller
public class ChatRoomSchemaController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private ChatRoomSchemaRepository roomRepo;

    @MessageMapping("/chat/initChat")
    public void initRoom(ChatRoomSchema room) throws InterruptedException {
    	//System.err.println("_______________________on init chat ________________________________");
        Optional<ChatRoomSchema> exist = roomRepo.findRoomByMembers(room.getMembers(), room.getMembers().size());
        ChatRoomSchema roomRes;
        if (!exist.isPresent()) {
            roomRes = roomRepo.save(room);
        } else {
            roomRes = exist.get();
        }
        List<String> members = roomRes.getMembers();
        for (int i = 0; i < members.size(); i++) {
            simpMessagingTemplate.convertAndSend("/chat/invite/" + members.get(i), roomRes);
        }
    }
}
