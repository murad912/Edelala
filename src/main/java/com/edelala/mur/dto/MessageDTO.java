package com.edelala.mur.dto;

import com.edelala.mur.entity.Message;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageDTO {
    private Long id;
    private Long rentRequestId;
    private Long senderId;
    private String senderUsername; // To display sender's name/email in UI
    private String content;
    private LocalDateTime timestamp;

    // Static method to convert Message entity to MessageDTO
    public static MessageDTO fromEntity(Message message) {
        return MessageDTO.builder()
                .id(message.getId())
                .rentRequestId(message.getRentRequest().getId())
                .senderId(message.getSender().getId())
                .senderUsername(message.getSender().getUsername()) // Or getEmail(), depending on what you want to display
                .content(message.getContent())
                .timestamp(message.getTimestamp())
                .build();
    }
}
