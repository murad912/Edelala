package com.edelala.mur.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Builder;
//@Entity
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//public class Payment {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    @ManyToOne
//    @JoinColumn(name = "renter_id")
//    private User renter;
//    @ManyToOne
//    @JoinColumn(name = "owner_id")
//    private User owner;
//    private BigDecimal amount;
//    private LocalDateTime paymentDate = LocalDateTime.now();
//    private String paymentIntentId; // From Stripe
//    private String paymentStatus; // SUCCESS, FAILED, PENDING
//    private String description; // "Broker service fee for property XYZ"
//    // ... other payment details
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
//    public User getOwner() {
//        return owner;
//    }
//
//    public void setOwner(User owner) {
//        this.owner = owner;
//    }
//
//    public BigDecimal getAmount() {
//        return amount;
//    }
//
//    public void setAmount(BigDecimal amount) {
//        this.amount = amount;
//    }
//
//    public LocalDateTime getPaymentDate() {
//        return paymentDate;
//    }
//
//    public void setPaymentDate(LocalDateTime paymentDate) {
//        this.paymentDate = paymentDate;
//    }
//
//    public String getPaymentIntentId() {
//        return paymentIntentId;
//    }
//
//    public void setPaymentIntentId(String paymentIntentId) {
//        this.paymentIntentId = paymentIntentId;
//    }
//
//    public String getPaymentStatus() {
//        return paymentStatus;
//    }
//
//    public void setPaymentStatus(String paymentStatus) {
//        this.paymentStatus = paymentStatus;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//}

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // Use LAZY fetch for performance
    @JoinColumn(name = "renter_id")
    private User renter;

    @ManyToOne(fetch = FetchType.LAZY) // Use LAZY fetch for performance
    @JoinColumn(name = "owner_id")
    private User owner; // The owner receiving the payment (from the property of the rent request)

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private String currency; // e.g., "usd" - NEW FIELD

    @Column(nullable = false)
    private LocalDateTime paymentDate = LocalDateTime.now();

    @Column(nullable = true) // Will be set when updated
    private LocalDateTime lastUpdatedDate; // NEW FIELD for tracking updates

    @Column(nullable = false, unique = true) // Stripe PaymentIntent ID should be unique
    private String stripePaymentIntentId; // Renamed for clarity in service calls

    @Column(nullable = false)
    private String status; // Payment status: e.g., 'requires_payment_method', 'succeeded', 'failed', 'canceled'

    @Column(nullable = true) // Description is optional
    private String description;

    @OneToOne(fetch = FetchType.LAZY) // One Payment for one RentRequest
    @JoinColumn(name = "rent_request_id", unique = true) // One-to-one link to RentRequest
    private RentRequest rentRequest; // NEW FIELD: Link to the associated rent request


    // Lombok's @Data annotation automatically handles all standard getters and setters.
    // Explicit getters and setters are listed below for clarity, but if Lombok is active,
    // they are not strictly necessary to be written out.
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getRenter() {
        return renter;
    }

    public void setRenter(User renter) {
        this.renter = renter;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() { // NEW GETTER
        return currency;
    }

    public void setCurrency(String currency) { // NEW SETTER
        this.currency = currency;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

    public LocalDateTime getLastUpdatedDate() { // NEW GETTER
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(LocalDateTime lastUpdatedDate) { // NEW SETTER
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public String getStripePaymentIntentId() { // Renamed getter
        return stripePaymentIntentId;
    }

    public void setStripePaymentIntentId(String stripePaymentIntentId) { // Renamed setter
        this.stripePaymentIntentId = stripePaymentIntentId;
    }

    public String getStatus() { // Renamed getter
        return status;
    }

    public void setStatus(String status) { // Renamed setter
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public RentRequest getRentRequest() { // NEW GETTER
        return rentRequest;
    }

    public void setRentRequest(RentRequest rentRequest) { // NEW SETTER
        this.rentRequest = rentRequest;
    }
}
