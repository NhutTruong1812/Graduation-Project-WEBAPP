package com.biglobby.server.chat.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.biglobby.server.chat.entity.MessageSchema;

public interface MessageSchemaRepository extends MongoRepository<MessageSchema, String> {

    @Query("{'roomId': ?0}")
    public List<MessageSchema> getByRoomId(String roomId);

    @Query("{'roomId': ?0}")
    public Page<MessageSchema> getByRoomId(String roomId, Pageable pageable);
}
