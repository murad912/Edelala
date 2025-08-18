package com.edelala.mur.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

//@Entity
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//public class RentRequest {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    @ManyToOne
//    @JoinColumn(name = "renter_id")
//    private User renter;
//    @ManyToOne
//    @JoinColumn(name = "property_id")
//    private Property property;
//    private LocalDateTime requestDate = LocalDateTime.now();
//    private String message;
//    private boolean isApproved = false;
//    // ... other request details
//
//    //Getter and Setter
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public User getRenter() {
//        return renter;
//    }
//
//    public void setRenter(User renter) {
//        this.renter = renter;
//    }
//
//    public Property getProperty() {
//        return property;
//    }
//
//    public void setProperty(Property property) {
//        this.property = property;
//    }
//
//    public LocalDateTime getRequestDate() {
//        return requestDate;
//    }
//
//    public void setRequestDate(LocalDateTime requestDate) {
//        this.requestDate = requestDate;
//    }
//
//    public String getMessage() {
//        return message;
//    }
//
//    public void setMessage(String message) {
//        this.message = message;
//    }
//
//    public boolean isApproved() {
//        return isApproved;
//    }
//
//    public void setApproved(boolean approved) {
//        isApproved = approved;
//    }
//} jun 6 working


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor; // Ensure this is imported for the constructor

import java.time.LocalDateTime;
import java.util.List;

//@Entity
//@Table(name = "rent_request")
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//public class RentRequest {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne(fetch = FetchType.LAZY) // Added FetchType for clarity
//    @JoinColumn(name = "renter_id", nullable = false) // Added nullable=false
//    private User renter;
//
//    @ManyToOne(fetch = FetchType.LAZY) // Added FetchType for clarity
//    @JoinColumn(name = "property_id", nullable = false) // Added nullable=false
//    private Property property;
//
//    @Column(name = "request_date", nullable = false) // Added nullable=false
//    private LocalDateTime requestDate;
//
//    @Column(name = "message", columnDefinition = "TEXT") // Added columnDefinition
//    private String message;
//
//    // --- CRITICAL FIX: Changed from boolean to Boolean wrapper type ---
//    @Column(name = "is_approved")
//    private Boolean isApproved; // true for approved, false for rejected, null for pending
//
//    @PrePersist // This method runs before a new entity is saved to the database
//    protected void onCreate() {
//        requestDate = LocalDateTime.now();
//        // Ensure isApproved is explicitly null for new requests to denote 'Pending'
//        if (this.isApproved == null) { // Check if it's already set (e.g., from DTO)
//            this.isApproved = null; // Set to null to ensure it's pending by default
//        }
//    }
//
//    // --- Getters and Setters (Updated to match Boolean type for isApproved) ---
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public User getRenter() {
//        return renter;
//    }
//
//    public void setRenter(User renter) {
//        this.renter = renter;
//    }
//
//    public Property getProperty() {
//        return property;
//    }
//
//    public void setProperty(Property property) {
//        this.property = property;
//    }
//
//    public LocalDateTime getRequestDate() {
//        return requestDate;
//    }
//
//    public void setRequestDate(LocalDateTime requestDate) {
//        this.requestDate = requestDate;
//    }
//
//    public String getMessage() {
//        return message;
//    }
//
//    public void setMessage(String message) {
//        this.message = message;
//    }
//
//    // Updated getter to return Boolean
//    public Boolean getIsApproved() {
//        return isApproved;
//    }
//
//    // Updated setter to accept Boolean
//    public void setIsApproved(Boolean approved) {
//        isApproved = approved;
//    }
//} jun 6 working
//


//@Entity
//@Table(name = "rent_request")
//@Getter // Lombok to generate getters
//@Setter // Lombok to generate setters
//@NoArgsConstructor // Lombok to generate no-argument constructor
//@AllArgsConstructor // Lombok to generate all-argument constructor
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) // Prevents serialization issues with lazy-loaded proxies
//public class RentRequest {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne(fetch = FetchType.LAZY) // Lazy fetching for performance
//    @JoinColumn(name = "property_id", nullable = false) // Foreign key to Property
//    private Property property;
//
//    @ManyToOne(fetch = FetchType.LAZY) // Lazy fetching for performance
//    @JoinColumn(name = "renter_id", nullable = false) // Foreign key to User (the renter)
//    private User renter;
//
//    @Column(name = "message", columnDefinition = "TEXT") // Store message as TEXT
//    private String message;
//
//    @Column(name = "request_date", nullable = false) // Timestamp of the request
//    private LocalDateTime requestDate;
//
//    // --- CRITICAL FIX: 'isApproved' MUST be a Boolean wrapper type ---
//    // true = Approved, false = Rejected, null = Pending
//    @Column(name = "is_approved")
//    private Boolean isApproved;
//
//    // This method runs automatically before a new entity is saved to the database.
//    // It's a JPA lifecycle callback.
//    @PrePersist
//    protected void onCreate() {
//        requestDate = LocalDateTime.now();
//        // Ensure that for a brand new request, the status is explicitly 'null' (Pending).
//        // This check prevents overriding a value if it's already set (e.g., from a DTO in more complex flows).
//        if (this.isApproved == null) {
//            this.isApproved = null;
//        }
//    }
//
//    // --- Manual Getters and Setters (if @Data, @Getter, @Setter are not used or to override Lombok) ---
//    // As Lombok is used, these are mostly for clarity.
//    public Long getId() { return id; }
//    public void setId(Long id) { this.id = id; }
//    public User getRenter() { return renter; }
//    public void setRenter(User renter) { this.renter = renter; }
//    public Property getProperty() { return property; }
//    public void setProperty(Property property) { this.property = property; }
//    public LocalDateTime getRequestDate() { return requestDate; }
//    public void setRequestDate(LocalDateTime requestDate) { this.requestDate = requestDate; }
//    public String getMessage() { return message; }
//    public void setMessage(String message) { this.message = message; }
//
//    // Getter for Boolean type: 'getIsApproved' (standard Java Bean convention for Boolean)
//    public Boolean getIsApproved() { return isApproved; }
//    // Setter for Boolean type: 'setIsApproved'
//    public void setIsApproved(Boolean approved) { isApproved = approved; }
//}  comment for delete functionality


//@Entity
//@Table(name = "rent_request")
//@Getter // Lombok to generate getters
//@Setter // Lombok to generate setters
//@NoArgsConstructor // Lombok to generate no-argument constructor
//@AllArgsConstructor // Lombok to generate all-argument constructor
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) // Prevents serialization issues with lazy-loaded proxies
//public class RentRequest {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne(fetch = FetchType.LAZY) // Lazy fetching for performance
//    @JoinColumn(name = "property_id", nullable = false) // Foreign key to Property
//    private Property property;
//
//    @ManyToOne(fetch = FetchType.LAZY) // Lazy fetching for performance
//    @JoinColumn(name = "renter_id", nullable = false) // Foreign key to User (the renter)
//    private User renter;
//
//    @Column(name = "message", columnDefinition = "TEXT") // Store message as TEXT
//    private String message;
//
//    @Column(name = "request_date", nullable = false) // Timestamp of the request
//    private LocalDateTime requestDate;
//
//    // --- CRITICAL FIX: 'isApproved' MUST be a Boolean wrapper type ---
//    // true = Approved, false = Rejected, null = Pending
//    @Column(name = "is_approved")
//    private Boolean isApproved;
//
//    // This method runs automatically before a new entity is saved to the database.
//    // It's a JPA lifecycle callback.
//    @PrePersist
//    protected void onCreate() {
//        requestDate = LocalDateTime.now();
//        // Ensure that for a brand new request, the status is explicitly 'null' (Pending).
//        // This check prevents overriding a value if it's already set (e.g., from a DTO in more complex flows).
//        if (this.isApproved == null) {
//            this.isApproved = null;
//        }
//    }
//
//    // --- Manual Getters and Setters (if @Data, @Getter, @Setter are not used or to override Lombok) ---
//    // As Lombok is used, these are mostly for clarity.
//    public Long getId() { return id; }
//    public void setId(Long id) { this.id = id; }
//    public User getRenter() { return renter; }
//    public void setRenter(User renter) { this.renter = renter; }
//    public Property getProperty() { return property; }
//    public void setProperty(Property property) { this.property = property; }
//    public LocalDateTime getRequestDate() { return requestDate; }
//    public void setRequestDate(LocalDateTime requestDate) { this.requestDate = requestDate; }
//    public String getMessage() { return message; }
//    public void setMessage(String message) { this.message = message; }
//
//    // Getter for Boolean type: 'getIsApproved' (standard Java Bean convention for Boolean)
//    public Boolean getIsApproved() { return isApproved; }
//    // Setter for Boolean type: 'setIsApproved'
//    public void setIsApproved(Boolean approved) { isApproved = approved; }
//}
// for payment

//@Entity
//@Table(name = "rent_request")
//@Getter // Lombok to generate getters
//@Setter // Lombok to generate setters
//@NoArgsConstructor // Lombok to generate no-argument constructor
//@AllArgsConstructor // Lombok to generate all-argument constructor
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) // Prevents serialization issues with lazy-loaded proxies
//public class RentRequest {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne(fetch = FetchType.LAZY) // Lazy fetching for performance
//    @JoinColumn(name = "property_id", nullable = false) // Foreign key to Property
//    private Property property;
//
//    @ManyToOne(fetch = FetchType.LAZY) // Lazy fetching for performance
//    @JoinColumn(name = "renter_id", nullable = false) // Foreign key to User (the renter)
//    private User renter;
//
//    @Column(name = "message", columnDefinition = "TEXT") // Store message as TEXT
//    private String message;
//
//    @Column(name = "request_date", nullable = false) // Timestamp of the request
//    private LocalDateTime requestDate;
//
//    @Column(name = "is_approved")
//    private Boolean isApproved; // true = Approved, false = Rejected, null = Pending
//
//    @Column(name = "payment_status") // NEW: Add payment status field
//    private String paymentStatus; // e.g., "pending", "succeeded", "failed"
//
//    // This method runs automatically before a new entity is saved to the database.
//    // It's a JPA lifecycle callback.
//    @PrePersist
//    protected void onCreate() {
//        requestDate = LocalDateTime.now();
//        // Ensure that for a brand new request, the status is explicitly 'null' (Pending).
//        // This check prevents overriding a value if it's already set (e.g., from a DTO in more complex flows).
//        if (this.isApproved == null) {
//            this.isApproved = null; // Default to pending approval
//        }
//        if (this.paymentStatus == null) { // NEW: Default payment status to pending
//            this.paymentStatus = "pending";
//        }
//    }
//} //comment to add messaging july 20


@Entity
@Table(name = "rent_request")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class RentRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id", nullable = false)
    private Property property;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "renter_id", nullable = false)
    private User renter;

    @Column(name = "message", columnDefinition = "TEXT")
    private String message;

    @Column(name = "request_date", nullable = false)
    private LocalDateTime requestDate;

    @Column(name = "is_approved")
    private Boolean isApproved;

    @Column(name = "payment_status")
    private String paymentStatus;

    // NEW: One-to-Many relationship with Message
    // @JsonManagedReference indicates this is the "forward" part of the relationship
    // and should be serialized. The 'back-reference' will be in Message.
    @JsonManagedReference // Add this annotation
    @OneToMany(mappedBy = "rentRequest", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Message> messages;

    @PrePersist
    protected void onCreate() {
        requestDate = LocalDateTime.now();
        if (this.isApproved == null) {
            this.isApproved = null;
        }
        if (this.paymentStatus == null) {
            this.paymentStatus = "pending";
        }
    }
}
