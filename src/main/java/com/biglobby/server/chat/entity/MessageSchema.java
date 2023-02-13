package com.biglobby.server.chat.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

import com.biglobby.server.chat.enumtypes.MessageType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document("messages")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MessageSchema {
    @Id
    private String id;

    private String message;
    private MessageType type;
    // Username nguoi gui
    private String sender;
    private String roomId;
    private List<String> readByRecipients;
    private Date createAt;
    private Date updateAt;
}
