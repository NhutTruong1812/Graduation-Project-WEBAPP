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

import com.biglobby.server.chat.entity.ChatRoomSchema;
import com.biglobby.server.chat.repository.ChatRoomSchemaRepository;
import com.biglobby.server.chat.service.ChatRoomSchemaService;

@Service
public class ChatRoomSchemaServiceImpl implements ChatRoomSchemaService {

    @Autowired
    private ChatRoomSchemaRepository roomRepo;

    @Override
    public ResponseEntity<List<ChatRoomSchema>> getAll() {
        return ResponseEntity.ok(roomRepo.findAll());
    }

    @Override
    public ResponseEntity<ChatRoomSchema> getById(String id) {
        Optional<ChatRoomSchema> roomSchema = roomRepo.findById(id);
        if (!roomSchema.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(roomSchema.get());
    }

    @Override
    public ResponseEntity<ChatRoomSchema> add(ChatRoomSchema room) {
        ChatRoomSchema added = roomRepo.save(room);
        if (added != null) {
            return ResponseEntity.ok(added);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @Override
    public ResponseEntity<ChatRoomSchema> update(String id, ChatRoomSchema room) {
        Optional<ChatRoomSchema> exist = roomRepo.findById(id);
        if (!exist.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        room.setId(id);
        ChatRoomSchema updated = roomRepo.save(room);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @Override
    public ResponseEntity<ChatRoomSchema> deleteById(String id) {
        Optional<ChatRoomSchema> exist = roomRepo.findById(id);
        if (!exist.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        roomRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Page<ChatRoomSchema>> getPageByUsername(Integer page, Integer limit, String username) {
        Pageable pageable = PageRequest.of(page, limit, Sort.by("updateAt").descending());
        return ResponseEntity.ok(roomRepo.getPageByUsername(username, pageable));
    }

    @Override
    public ResponseEntity<List<ChatRoomSchema>> getByUsername(String username) {
        return ResponseEntity.ok(roomRepo.getByUsername(username));
    }

}
