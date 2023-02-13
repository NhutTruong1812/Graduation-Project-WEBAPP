package com.biglobby.server.chat.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document("rooms")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChatRoomSchema {
    
    @Id
    private String id;

    private String initator;
    //Danh sách username
    private List<String> members;
    private Date createAt;
    private Date updateAt;
}
