package com.edelala.mur.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // The user who receives the notification
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipient_id", nullable = false)
    private User recipient;

    // The user who triggered the notification (e.g., owner sending a message, renter making a request)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id") // Can be null if system-generated
    private User sender;

    @Column(nullable = false)
    private String type; // e.g., "NEW_MESSAGE", "REQUEST_APPROVED", "REQUEST_REJECTED", "PROPERTY_UPDATE"

    @Column(nullable = false, columnDefinition = "TEXT")
    private String message;

    @Column(nullable = false)
    private boolean isRead;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime timestamp;

    // Optional: ID of the related entity (e.g., rentRequestId, propertyId) for deep linking
    @Column(name = "related_entity_id")
    private Long relatedEntityId;

    // Optional: Type of the related entity (e.g., "RENT_REQUEST", "PROPERTY")
    @Column(name = "related_entity_type")
    private String relatedEntityType;
}
