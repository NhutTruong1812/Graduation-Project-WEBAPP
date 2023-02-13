package com.biglobby.server.chat.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import com.biglobby.server.chat.entity.ChatRoomSchema;

public interface ChatRoomSchemaService {

    public ResponseEntity<List<ChatRoomSchema>> getAll();

    public ResponseEntity<ChatRoomSchema> getById(String id);

    public ResponseEntity<List<ChatRoomSchema>> getByUsername(String username);

    public ResponseEntity<Page<ChatRoomSchema>> getPageByUsername(Integer page, Integer limit, String username);

    public ResponseEntity<ChatRoomSchema> add(ChatRoomSchema room);

    public ResponseEntity<ChatRoomSchema> update(String id, ChatRoomSchema room);

    public ResponseEntity<ChatRoomSchema> deleteById(String id);
}
