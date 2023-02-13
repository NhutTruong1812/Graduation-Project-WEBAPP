package com.biglobby.server.chat.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.biglobby.server.chat.entity.UserSchema;

public interface UserSchemaService {

    public ResponseEntity<List<UserSchema>> getAll();

    public ResponseEntity<UserSchema> getById(String id);

    public ResponseEntity<UserSchema> add(UserSchema user);

    public ResponseEntity<UserSchema> update(String id, UserSchema user);

    public ResponseEntity<Void> deleteById(String id);
}
