package com.edelala.mur.dto;

import com.edelala.mur.entity.Notification;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationDTO {
    private Long id;
    private Long recipientId;
    private Long senderId;
    private String senderUsername; // Display name/email of the sender
    private String type;
    private String message;
    private boolean isRead;
    private LocalDateTime timestamp;
    private Long relatedEntityId;
    private String relatedEntityType;

    // Static method to convert Notification entity to NotificationDTO
    public static NotificationDTO fromEntity(Notification notification) {
        return NotificationDTO.builder()
                .id(notification.getId())
                .recipientId(notification.getRecipient().getId())
                .senderId(notification.getSender() != null ? notification.getSender().getId() : null)
                .senderUsername(notification.getSender() != null ? notification.getSender().getUsername() : "System")
                .type(notification.getType())
                .message(notification.getMessage())
                .isRead(notification.isRead())
                .timestamp(notification.getTimestamp())
                .relatedEntityId(notification.getRelatedEntityId())
                .relatedEntityType(notification.getRelatedEntityType())
                .build();
    }
}
