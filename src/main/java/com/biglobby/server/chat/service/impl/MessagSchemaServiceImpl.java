package com.biglobby.server.chat.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.biglobby.server.chat.entity.MessageSchema;
import com.biglobby.server.chat.repository.MessageSchemaRepository;
import com.biglobby.server.chat.service.MessageSchemaService;

@Service
public class MessagSchemaServiceImpl implements MessageSchemaService {

    @Autowired
    private MessageSchemaRepository messageRepo;

    @Override
    public ResponseEntity<List<MessageSchema>> getAll() {
        return ResponseEntity.ok(messageRepo.findAll());
    }

    @Override
    public ResponseEntity<MessageSchema> getById(String id) {
        Optional<MessageSchema> message = messageRepo.findById(id);
        if (!message.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(message.get());
    }

    @Override
    public ResponseEntity<MessageSchema> add(MessageSchema message) {
        MessageSchema added = messageRepo.save(message);
        if (added != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(added);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @Override
    public ResponseEntity<MessageSchema> update(String id, MessageSchema message) {
        Optional<MessageSchema> exist = messageRepo.findById(id);
        if (!exist.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        message.setId(id);
        MessageSchema updated = messageRepo.save(message);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @Override
    public ResponseEntity<MessageSchema> deleteById(String id) {
        Optional<MessageSchema> exist = messageRepo.findById(id);
        if (!exist.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        messageRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Page<MessageSchema>> getPageByRoomId(String roomId, Integer pagenum, Integer limit) {
        Pageable pageable = PageRequest.of(pagenum, limit, Sort.by("createAt").descending());
        return ResponseEntity.ok(messageRepo.getByRoomId(roomId, pageable));
    }

}
