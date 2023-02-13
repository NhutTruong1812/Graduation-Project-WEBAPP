package com.biglobby.mongodb.document;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document("user")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {

    @Id
    private String id;

    private String username;
    private String email;
}
