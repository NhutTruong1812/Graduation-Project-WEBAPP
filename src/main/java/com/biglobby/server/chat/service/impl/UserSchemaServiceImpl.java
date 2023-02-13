package com.biglobby.server.chat.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.biglobby.server.chat.entity.UserSchema;
import com.biglobby.server.chat.repository.UserSchemaRepository;
import com.biglobby.server.chat.service.UserSchemaService;

@Service
public class UserSchemaServiceImpl implements UserSchemaService {

    @Autowired
    private UserSchemaRepository userRepo;

    @Override
    public ResponseEntity<List<UserSchema>> getAll() {
        return ResponseEntity.ok(userRepo.findAll());
    }

    @Override
    public ResponseEntity<UserSchema> getById(String id) {
        Optional<UserSchema> user = userRepo.findById(id);
        if (!user.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user.get());
    }

    @Override
    public ResponseEntity<UserSchema> add(UserSchema user) {
        UserSchema added = userRepo.save(user);
        if (added != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(added);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @Override
    public ResponseEntity<UserSchema> update(String id, UserSchema user) {
        Optional<UserSchema> exist = userRepo.findById(id);
        if (!exist.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        user.setId(exist.get().getId());
        UserSchema updated = userRepo.save(user);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @Override
    public ResponseEntity<Void> deleteById(String id) {
        Optional<UserSchema> user = userRepo.findById(id);
        if (!user.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        userRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
