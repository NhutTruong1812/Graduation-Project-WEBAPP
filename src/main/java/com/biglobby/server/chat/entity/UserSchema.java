package com.biglobby.server.chat.entity;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document("users")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserSchema {

    @Id
    private String id;

    private String username;
    private String fullname;
    private String email;
}
