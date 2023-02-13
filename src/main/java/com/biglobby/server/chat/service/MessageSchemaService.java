package com.biglobby.server.chat.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import com.biglobby.server.chat.entity.MessageSchema;

public interface MessageSchemaService {

    public ResponseEntity<List<MessageSchema>> getAll();

    public ResponseEntity<MessageSchema> getById(String id);

    public ResponseEntity<Page<MessageSchema>> getPageByRoomId(String roomId, Integer pagenum, Integer limit);
    
    public ResponseEntity<MessageSchema> add(MessageSchema message);

    public ResponseEntity<MessageSchema> update(String id, MessageSchema message);

    public ResponseEntity<MessageSchema> deleteById(String id);
    
}
