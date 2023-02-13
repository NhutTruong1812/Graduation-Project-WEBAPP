package com.biglobby.server.chat.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.biglobby.server.chat.entity.ChatRoomSchema;

public interface ChatRoomSchemaRepository extends MongoRepository<ChatRoomSchema, String> {

    @Query("{'members': ?0}")
    public Page<ChatRoomSchema> getPageByUsername(String username, Pageable pageable);

    @Query("{'members': ?0}")
    public List<ChatRoomSchema> getByUsername(String username);

    @Query("{'members': {$size:?1, $all: ?0}}")
    public Optional<ChatRoomSchema> findRoomByMembers(List<String> members, int size);
}
