package com.edelala.mur.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Many messages can belong to one rent request
    // @JsonBackReference indicates this is the "back" part of the relationship
    // and should be ignored during serialization to break circular dependency.
    @JsonBackReference // Add this annotation
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rent_request_id", nullable = false)
    private RentRequest rentRequest;

    // Many messages can be sent by one user
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime timestamp;
}
