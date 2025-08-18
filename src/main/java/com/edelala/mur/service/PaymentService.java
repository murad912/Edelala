package com.edelala.mur.service;


import com.edelala.mur.entity.Payment; // You'll create this entity next
import com.edelala.mur.entity.Property;
import com.edelala.mur.entity.RentRequest;
import com.edelala.mur.entity.User;
import com.edelala.mur.repo.PaymentRepository;
import com.edelala.mur.repo.PropertyRepository;
import com.edelala.mur.repo.RentRequestRepository;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

//@Service
//@RequiredArgsConstructor
//public class PaymentService {
//
//    private final StripeService stripeService; // Inject StripeService for Stripe API calls
//    private final PaymentRepository paymentRepository; // To save payment records
//    private final RentRequestRepository rentRequestRepository; // To link payments to rent requests
//
//    /**
//     * Creates a Stripe Payment Intent for a given amount.
//     * This method also creates a pending payment record in your database, linked to a specific rent request and renter.
//     *
//     * @param amount The amount to charge (e.g., broker service fee).
//     * @param description A description for the payment intent.
//     * @param rentRequestId The ID of the rent request this payment is for.
//     * @param renter The user initiating the payment.
//     * @return The created PaymentIntent object from Stripe.
//     * @throws StripeException If there's an error interacting with the Stripe API.
//     * @throws RuntimeException If the rent request is not found or not approved, or if the renter is not authorized.
//     */
//    @Transactional
//    public PaymentIntent createPaymentIntent(BigDecimal amount, String description, Long rentRequestId, User renter) throws StripeException {
//        // Find the rent request to ensure it exists and is approved
//        RentRequest rentRequest = rentRequestRepository.findById(rentRequestId)
//                .orElseThrow(() -> new RuntimeException("Rent Request not found with ID: " + rentRequestId));
//
//        // Ensure the rent request is approved and belongs to the current renter
//        if (rentRequest.getIsApproved() == null || !rentRequest.getIsApproved()) {
//            throw new RuntimeException("Rent Request must be approved to create a payment intent.");
//        }
//        if (!rentRequest.getRenter().getId().equals(renter.getId())) {
//            throw new RuntimeException("You do not have permission to create a payment for this rent request.");
//        }
//
//        // Convert BigDecimal amount to long (cents) for Stripe
//        // Stripe amounts are in the smallest currency unit (e.g., cents for USD)
//        Long amountInCents = amount.multiply(new BigDecimal("100")).longValue();
//
//        // Use StripeService to create the PaymentIntent
//        PaymentIntent paymentIntent = stripeService.createPaymentIntent(amountInCents, "usd", description); // Assuming USD for now
//
//        // Create a new Payment record in your database as 'PENDING'
//        Payment payment = new Payment();
//        payment.setStripePaymentIntentId(paymentIntent.getId()); // Store Stripe's unique ID
//        payment.setAmount(amount);
//        payment.setCurrency(paymentIntent.getCurrency());
//        payment.setStatus(paymentIntent.getStatus()); // e.g., 'requires_payment_method'
//        payment.setRenter(renter);
//        payment.setRentRequest(rentRequest); // Link to the rent request
//        payment.setPaymentDate(LocalDateTime.now()); // Set initial date
//
//        paymentRepository.save(payment); // Save the pending payment record
//
//        return paymentIntent;
//    }
//
//    /**
//     * Updates the status of a payment record in the database based on Stripe's payment intent status.
//     * This would typically be called from the confirm-payment endpoint or a webhook.
//     *
//     * @param paymentIntentId The Stripe Payment Intent ID.
//     * @param status The new status (e.g., "succeeded", "failed").
//     * @throws RuntimeException If the payment record is not found.
//     */
//    @Transactional
//    public void updatePaymentStatus(String paymentIntentId, String status) {
//        // Find the payment record by its Stripe Payment Intent ID
//        Payment payment = paymentRepository.findByStripePaymentIntentId(paymentIntentId)
//                .orElseThrow(() -> new RuntimeException("Payment record not found for Stripe Intent ID: " + paymentIntentId));
//
//        payment.setStatus(status); // Update the status
//        payment.setLastUpdatedDate(LocalDateTime.now()); // Update last updated date
//        paymentRepository.save(payment); // Save the updated payment record
//    }
//}
// //jun 12 modify for payment to fix
//@Service
//@RequiredArgsConstructor
//public class PaymentService {
//
//    private final StripeService stripeService;
//    private final PaymentRepository paymentRepository;
//    private final RentRequestRepository rentRequestRepository;
//    private final PropertyRepository propertyRepository; // Inject PropertyRepository
//
//    /**
//     * Creates a Stripe Payment Intent for a given amount and currency.
//     * This method also creates a pending payment record in your database,
//     * linked to a specific rent request, renter, and the property owner.
//     *
//     * @param amount The amount to charge (from frontend).
//     * @param currency The currency (from frontend).
//     * @param description A description for the payment intent.
//     * @param rentRequestId The ID of the rent request this payment is for.
//     * @param propertyId The ID of the property associated with the rent request.
//     * @param renter The user initiating the payment.
//     * @return The created PaymentIntent object from Stripe.
//     * @throws StripeException If there's an error interacting with the Stripe API.
//     * @throws RuntimeException If rent request or property is not found, or not approved.
//     */
//    @Transactional
//    public PaymentIntent createPaymentIntent(
//            BigDecimal amount,
//            String currency,
//            String description,
//            Long rentRequestId,
//            Long propertyId, // New parameter
//            User renter
//    ) throws StripeException {
//        // Find the rent request to ensure it exists and is approved
//        RentRequest rentRequest = rentRequestRepository.findById(rentRequestId)
//                .orElseThrow(() -> new RuntimeException("Rent Request not found with ID: " + rentRequestId));
//
//        // Find the property to get the owner
//        Property property = propertyRepository.findById(propertyId)
//                .orElseThrow(() -> new RuntimeException("Property not found with ID: " + propertyId));
//
//        // Ensure the rent request is approved and belongs to the current renter
//        if (rentRequest.getIsApproved() == null || !rentRequest.getIsApproved()) {
//            throw new RuntimeException("Rent Request must be approved to create a payment intent.");
//        }
//        if (!rentRequest.getRenter().getId().equals(renter.getId())) {
//            throw new RuntimeException("You do not have permission to create a payment for this rent request.");
//        }
//        // Ensure the property linked to the rent request matches the propertyId provided
//        if (!rentRequest.getProperty().getId().equals(propertyId)) {
//            throw new RuntimeException("Property ID mismatch for the given rent request.");
//        }
//        // Ensure the property has an owner
//        if (property.getOwner() == null) {
//            throw new RuntimeException("Property does not have an associated owner.");
//        }
//
//        // Convert BigDecimal amount to long (cents) for Stripe
//        Long amountInCents = amount.multiply(new BigDecimal("100")).longValue();
//
//        // Use StripeService to create the PaymentIntent
//        PaymentIntent paymentIntent = stripeService.createPaymentIntent(amountInCents, currency, description);
//
//        // Create a new Payment record in your database as 'PENDING'
//        Payment payment = new Payment();
//        payment.setStripePaymentIntentId(paymentIntent.getId()); // Store Stripe's unique ID
//        payment.setAmount(amount);
//        payment.setCurrency(paymentIntent.getCurrency());
//        payment.setStatus(paymentIntent.getStatus()); // e.g., 'requires_payment_method'
//        payment.setRenter(renter);
//        payment.setOwner(property.getOwner()); // Set the owner from the property
//        payment.setRentRequest(rentRequest); // Link to the rent request
//        payment.setPaymentDate(LocalDateTime.now()); // Set initial date
//
//        paymentRepository.save(payment); // Save the pending payment record
//
//        return paymentIntent;
//    }
//
//    /**
//     * Updates the status of a payment record in the database based on Stripe's payment intent status.
//     * This would typically be called from the confirm-payment endpoint or a webhook.
//     *
//     * @param paymentIntentId The Stripe Payment Intent ID.
//     * @param status The new status (e.g., "succeeded", "failed").
//     * @param rentRequestId The ID of the rent request associated with the payment.
//     * @param authenticatedRenter The authenticated user who is trying to confirm.
//     * @throws RuntimeException If the payment record is not found or not authorized.
//     */
//    @Transactional
//    public void updatePaymentStatus(String paymentIntentId, String status, Long rentRequestId, User authenticatedRenter) {
//        // Find the payment record by its Stripe Payment Intent ID
//        Payment payment = paymentRepository.findByStripePaymentIntentId(paymentIntentId)
//                .orElseThrow(() -> new RuntimeException("Payment record not found for Stripe Intent ID: " + paymentIntentId));
//
//        // CRUCIAL: Add authorization check to ensure the authenticated renter is the one associated with the payment
//        if (!payment.getRenter().getId().equals(authenticatedRenter.getId())) {
//            throw new RuntimeException("Unauthorized: You do not have permission to update this payment status.");
//        }
//        // Optional: Also check if the rentRequestId matches, though paymentIntentId should be unique
//        if (rentRequestId != null && !payment.getRentRequest().getId().equals(rentRequestId)) {
//            throw new RuntimeException("Rent Request ID mismatch for the payment confirmation.");
//        }
//
//
//        payment.setStatus(status); // Update the status
//        payment.setLastUpdatedDate(LocalDateTime.now()); // Update last updated date
//        paymentRepository.save(payment); // Save the updated payment record
//    }
//
//    // New method to expose publishable key from StripeService
//    public String getStripePublishableKey() {
//        return stripeService.getPublishableKey();
//    }
//} jun 13

//@Service
//@RequiredArgsConstructor
//public class PaymentService {
//
//    private final StripeService stripeService;
//    private final PaymentRepository paymentRepository;
//    private final RentRequestRepository rentRequestRepository;
//    private final PropertyRepository propertyRepository;
//
//    /**
//     * Creates a Stripe Payment Intent for a given amount and currency.
//     * It first checks for an existing payment record for the rent request.
//     * If a pending one exists, it retrieves and returns its client secret from Stripe.
//     * If a succeeded one exists, it throws an error.
//     *
//     * @param amount The amount to charge (from frontend).
//     * @param currency The currency (from frontend, e.g., "USD").
//     * @param description A description for the payment intent.
//     * @param rentRequestId The ID of the rent request this payment is for.
//     * @param propertyId The ID of the property associated with the rent request.
//     * @param renter The user initiating the payment.
//     * @return The created (or existing) PaymentIntent object from Stripe.
//     * @throws StripeException If there's an error interacting with the Stripe API.
//     * @throws RuntimeException If rent request or property is not found, not approved,
//     * or if payment already completed.
//     */
//    @Transactional
//    public PaymentIntent createPaymentIntent(
//            BigDecimal amount,
//            String currency,
//            String description,
//            Long rentRequestId,
//            Long propertyId,
//            User renter
//    ) throws StripeException {
//        // 1. Validate Rent Request and Property existence and status
//        RentRequest rentRequest = rentRequestRepository.findById(rentRequestId)
//                .orElseThrow(() -> new RuntimeException("Rent Request not found with ID: " + rentRequestId));
//
//        Property property = propertyRepository.findById(propertyId)
//                .orElseThrow(() -> new RuntimeException("Property not found with ID: " + propertyId));
//
//        if (rentRequest.getIsApproved() == null || !rentRequest.getIsApproved()) {
//            throw new RuntimeException("Rent Request must be approved to create a payment intent.");
//        }
//        if (!rentRequest.getRenter().getId().equals(renter.getId())) {
//            throw new RuntimeException("You do not have permission to create a payment for this rent request.");
//        }
//        if (!rentRequest.getProperty().getId().equals(propertyId)) {
//            throw new RuntimeException("Property ID mismatch for the given rent request. Please ensure correct data.");
//        }
//        if (property.getOwner() == null) {
//            throw new RuntimeException("Property does not have an associated owner.");
//        }
//
//        // 2. CHECK FOR EXISTING PAYMENT FOR THIS RENT REQUEST
//        Optional<Payment> existingPaymentOptional = paymentRepository.findByRentRequestId(rentRequestId);
//
//        if (existingPaymentOptional.isPresent()) {
//            Payment existingPayment = existingPaymentOptional.get();
//            // If payment already succeeded, prevent new payment creation and inform user
//            if ("succeeded".equals(existingPayment.getStatus())) {
//                throw new RuntimeException("Payment has already been successfully completed for this rent request.");
//            }
//            // If payment is in a "pending" or "requires_payment_method" state,
//            // or even "failed", we retrieve the *existing* Stripe PaymentIntent
//            // This allows the user to retry payment without creating new records
//            System.out.println("PaymentService: Existing payment found for Rent Request ID " + rentRequestId +
//                    " (Stripe Intent ID: " + existingPayment.getStripePaymentIntentId() + "). Status: " + existingPayment.getStatus() +
//                    ". Retrieving and returning existing Stripe PaymentIntent.");
//
//            // Retrieve the existing PaymentIntent from Stripe
//            return stripeService.retrievePaymentIntent(existingPayment.getStripePaymentIntentId());
//        }
//
//        // 3. If no existing payment record, create a new one
//        Long amountInCents = amount.multiply(new BigDecimal("100")).longValue(); // Convert BigDecimal to cents
//
//        System.out.println("PaymentService: No existing payment found. Creating a NEW Stripe PaymentIntent for amount: " + amountInCents + " " + currency);
//        PaymentIntent paymentIntent = stripeService.createPaymentIntent(amountInCents, currency, description);
//
//        // 4. Save the new Payment record in your database as 'PENDING'
//        Payment payment = new Payment();
//        payment.setStripePaymentIntentId(paymentIntent.getId()); // Store Stripe's unique ID
//        payment.setAmount(amount);
//        payment.setCurrency(paymentIntent.getCurrency());
//        payment.setStatus(paymentIntent.getStatus()); // e.g., 'requires_payment_method'
//        payment.setRenter(renter);
//        payment.setOwner(property.getOwner()); // Set the owner from the property
//        payment.setRentRequest(rentRequest); // Link to the rent request
//        payment.setPaymentDate(LocalDateTime.now()); // Set initial creation date
//
//        paymentRepository.save(payment); // Save the pending payment record
//
//        return paymentIntent;
//    }
//
//    /**
//     * Updates the status of a payment record in the database based on Stripe's payment intent status.
//     * This method is called after a payment is confirmed by Stripe (e.g., from frontend or webhook).
//     *
//     * @param paymentIntentId The Stripe Payment Intent ID.
//     * @param status The new status (e.g., "succeeded", "failed").
//     * @param rentRequestId The ID of the rent request associated with the payment (optional, for extra validation).
//     * @param authenticatedRenter The authenticated user who is trying to confirm.
//     * @throws RuntimeException If the payment record is not found or not authorized.
//     */
//    @Transactional
//    public void updatePaymentStatus(String paymentIntentId, String status, Long rentRequestId, User authenticatedRenter) {
//        Payment payment = paymentRepository.findByStripePaymentIntentId(paymentIntentId)
//                .orElseThrow(() -> new RuntimeException("Payment record not found for Stripe Intent ID: " + paymentIntentId));
//
//        // Authorization check: Ensure the authenticated user is the renter associated with this payment
//        if (!payment.getRenter().getId().equals(authenticatedRenter.getId())) {
//            throw new RuntimeException("Unauthorized: You do not have permission to update this payment status.");
//        }
//        // Optional validation: Ensure rentRequestId matches if provided
//        if (rentRequestId != null && !payment.getRentRequest().getId().equals(rentRequestId)) {
//            throw new RuntimeException("Rent Request ID mismatch for the payment confirmation. Data integrity error.");
//        }
//
//        payment.setStatus(status); // Update the status (e.g., to "succeeded")
//        payment.setLastUpdatedDate(LocalDateTime.now()); // Update last updated date
//        paymentRepository.save(payment); // Save the updated payment record
//        System.out.println("PaymentService: Payment status updated to '" + status + "' for intent " + paymentIntentId);
//    }
//
//    public String getStripePublishableKey() {
//        return stripeService.getPublishableKey();
//    }
//}

//@Service
//@RequiredArgsConstructor
//public class PaymentService {
//
//    private final StripeService stripeService;
//    private final PaymentRepository paymentRepository;
//    private final RentRequestRepository rentRequestRepository;
//    private final PropertyRepository propertyRepository;
//
//    @Transactional
//    public PaymentIntent createPaymentIntent(
//            BigDecimal amount,
//            String currency,
//            String description,
//            Long rentRequestId,
//            Long propertyId,
//            User renter
//    ) throws StripeException {
//        RentRequest rentRequest = rentRequestRepository.findById(rentRequestId)
//                .orElseThrow(() -> new RuntimeException("Rent Request not found with ID: " + rentRequestId));
//
//        Property property = propertyRepository.findById(propertyId)
//                .orElseThrow(() -> new RuntimeException("Property not found with ID: " + propertyId));
//
//        if (rentRequest.getIsApproved() == null || !rentRequest.getIsApproved()) {
//            throw new RuntimeException("Rent Request must be approved to create a payment intent.");
//        }
//        if (!rentRequest.getRenter().getId().equals(renter.getId())) {
//            throw new RuntimeException("You do not have permission to create a payment for this rent request.");
//        }
//        if (!rentRequest.getProperty().getId().equals(propertyId)) {
//            throw new RuntimeException("Property ID mismatch for the given rent request. Please ensure correct data.");
//        }
//        if (property.getOwner() == null) {
//            throw new RuntimeException("Property does not have an associated owner.");
//        }
//
//        Optional<Payment> existingPaymentOptional = paymentRepository.findByRentRequestId(rentRequestId);
//
//        if (existingPaymentOptional.isPresent()) {
//            Payment existingPayment = existingPaymentOptional.get();
//            if ("succeeded".equals(existingPayment.getStatus())) {
//                throw new RuntimeException("Payment has already been successfully completed for this rent request.");
//            }
//            System.out.println("PaymentService: Existing payment found for Rent Request ID " + rentRequestId +
//                    " (Stripe Intent ID: " + existingPayment.getStripePaymentIntentId() + "). Status: " + existingPayment.getStatus() +
//                    ". Retrieving and returning existing Stripe PaymentIntent.");
//
//            return stripeService.retrievePaymentIntent(existingPayment.getStripePaymentIntentId());
//        }
//
//        Long amountInCents = amount.multiply(new BigDecimal("100")).longValue();
//
//        System.out.println("PaymentService: No existing payment found. Creating a NEW Stripe PaymentIntent for amount: " + amountInCents + " " + currency);
//        PaymentIntent paymentIntent = stripeService.createPaymentIntent(amountInCents, currency, description);
//
//        Payment payment = new Payment();
//        payment.setStripePaymentIntentId(paymentIntent.getId());
//        payment.setAmount(amount);
//        payment.setCurrency(paymentIntent.getCurrency());
//        payment.setStatus(paymentIntent.getStatus());
//        payment.setRenter(renter);
//        payment.setOwner(property.getOwner());
//        payment.setRentRequest(rentRequest);
//        payment.setPaymentDate(LocalDateTime.now());
//
//        paymentRepository.save(payment);
//
//        return paymentIntent;
//    }
//
//    @Transactional
//    public void updatePaymentStatus(String paymentIntentId, String status, Long rentRequestId, User authenticatedRenter) {
//        Payment payment = paymentRepository.findByStripePaymentIntentId(paymentIntentId)
//                .orElseThrow(() -> new RuntimeException("Payment record not found for Stripe Intent ID: " + paymentIntentId));
//
//        if (!payment.getRenter().getId().equals(authenticatedRenter.getId())) {
//            throw new RuntimeException("Unauthorized: You do not have permission to update this payment status.");
//        }
//        if (rentRequestId != null && !payment.getRentRequest().getId().equals(rentRequestId)) {
//            throw new RuntimeException("Rent Request ID mismatch for the payment confirmation. Data integrity error.");
//        }
//
//        payment.setStatus(status);
//        payment.setLastUpdatedDate(LocalDateTime.now());
//        paymentRepository.save(payment);
//        System.out.println("PaymentService: Payment status updated to '" + status + "' for intent " + paymentIntentId);
//    }
//
//    public String getStripePublishableKey() {
//        return stripeService.getPublishableKey();
//    }
//} jun 13 12:31 am

//@Service
//@RequiredArgsConstructor
//public class PaymentService {
//
//    private final StripeService stripeService;
//    private final PaymentRepository paymentRepository;
//    private final RentRequestRepository rentRequestRepository;
//    private final PropertyRepository propertyRepository;
//
//    @Transactional
//    public PaymentIntent createPaymentIntent(
//            BigDecimal amount,
//            String currency,
//            String description,
//            Long rentRequestId,
//            Long propertyId,
//            User renter
//    ) throws StripeException {
//        RentRequest rentRequest = rentRequestRepository.findById(rentRequestId)
//                .orElseThrow(() -> new RuntimeException("Rent Request not found with ID: " + rentRequestId));
//
//        Property property = propertyRepository.findById(propertyId)
//                .orElseThrow(() -> new RuntimeException("Property not found with ID: " + propertyId));
//
//        if (rentRequest.getIsApproved() == null || !rentRequest.getIsApproved()) {
//            throw new RuntimeException("Rent Request must be approved to create a payment intent.");
//        }
//        if (!rentRequest.getRenter().getId().equals(renter.getId())) {
//            throw new RuntimeException("You do not have permission to create a payment for this rent request.");
//        }
//        if (!rentRequest.getProperty().getId().equals(propertyId)) {
//            throw new RuntimeException("Property ID mismatch for the given rent request. Please ensure correct data.");
//        }
//        if (property.getOwner() == null) {
//            throw new RuntimeException("Property does not have an associated owner.");
//        }
//
//        Optional<Payment> existingPaymentOptional = paymentRepository.findByRentRequestId(rentRequestId);
//
//        if (existingPaymentOptional.isPresent()) {
//            Payment existingPayment = existingPaymentOptional.get();
//            if ("succeeded".equals(existingPayment.getStatus())) {
//                throw new RuntimeException("Payment has already been successfully completed for this rent request.");
//            }
//            System.out.println("PaymentService: Existing payment found for Rent Request ID " + rentRequestId +
//                    " (Stripe Intent ID: " + existingPayment.getStripePaymentIntentId() + "). Status: " + existingPayment.getStatus() +
//                    ". Retrieving and returning existing Stripe PaymentIntent.");
//
//            return stripeService.retrievePaymentIntent(existingPayment.getStripePaymentIntentId());
//        }
//
//        Long amountInCents = amount.multiply(new BigDecimal("100")).longValue();
//
//        System.out.println("PaymentService: No existing payment found. Creating a NEW Stripe PaymentIntent for amount: " + amountInCents + " " + currency);
//        PaymentIntent paymentIntent = stripeService.createPaymentIntent(amountInCents, currency, description);
//
//        Payment payment = new Payment();
//        payment.setStripePaymentIntentId(paymentIntent.getId());
//        payment.setAmount(amount);
//        payment.setCurrency(paymentIntent.getCurrency());
//        payment.setStatus(paymentIntent.getStatus());
//        payment.setRenter(renter);
//        payment.setOwner(property.getOwner());
//        payment.setRentRequest(rentRequest);
//        payment.setPaymentDate(LocalDateTime.now());
//
//        paymentRepository.save(payment); // This is line 411 where the error is occurring
//
//        return paymentIntent;
//    }
//
//    @Transactional
//    public void updatePaymentStatus(String paymentIntentId, String status, Long rentRequestId, User authenticatedRenter) {
//        Payment payment = paymentRepository.findByStripePaymentIntentId(paymentIntentId)
//                .orElseThrow(() -> new RuntimeException("Payment record not found for Stripe Intent ID: " + paymentIntentId));
//
//        if (!payment.getRenter().getId().equals(authenticatedRenter.getId())) {
//            throw new RuntimeException("Unauthorized: You do not have permission to update this payment status.");
//        }
//        if (rentRequestId != null && !payment.getRentRequest().getId().equals(rentRequestId)) {
//            throw new RuntimeException("Rent Request ID mismatch for the payment confirmation. Data integrity error.");
//        }
//
//        payment.setStatus(status);
//        payment.setLastUpdatedDate(LocalDateTime.now());
//        paymentRepository.save(payment);
//        System.out.println("PaymentService: Payment status updated to '" + status + "' for intent " + paymentIntentId);
//    }
//
//    public String getStripePublishableKey() {
//        return stripeService.getPublishableKey();
//    }
//}
// jun 14 12:00pm

//@Service
//@RequiredArgsConstructor
//public class PaymentService {
//
//    private final StripeService stripeService;
//    private final PaymentRepository paymentRepository;
//    private final RentRequestRepository rentRequestRepository;
//    private final PropertyRepository propertyRepository;
//
//    @Transactional
//    public PaymentIntent createPaymentIntent(
//            BigDecimal amount,
//            String currency,
//            String description,
//            Long rentRequestId,
//            Long propertyId,
//            User renter
//    ) throws StripeException {
//        System.out.println("PaymentService: Entering createPaymentIntent for Rent Request ID: " + rentRequestId + " on thread: " + Thread.currentThread().getName());
//
//        // 1. Check if Rent Request exists
//        RentRequest rentRequest = rentRequestRepository.findById(rentRequestId)
//                .orElseThrow(() -> {
//                    System.out.println("PaymentService: Rent Request not found with ID: " + rentRequestId);
//                    return new RuntimeException("Rent Request not found with ID: " + rentRequestId);
//                });
//        System.out.println("PaymentService: Found Rent Request ID: " + rentRequest.getId() + ", Approved: " + rentRequest.getIsApproved() + " on thread: " + Thread.currentThread().getName());
//
//
//        // 2. Check if Property exists (this is important for later logic)
//        Property property = propertyRepository.findById(propertyId)
//                .orElseThrow(() -> {
//                    System.out.println("PaymentService: Property not found with ID: " + propertyId);
//                    return new RuntimeException("Property not found with ID: " + propertyId);
//                });
//        System.out.println("PaymentService: Found Property ID: " + property.getId() + " on thread: " + Thread.currentThread().getName());
//
//        if (rentRequest.getIsApproved() == null || !rentRequest.getIsApproved()) {
//            System.out.println("PaymentService: Rent Request ID " + rentRequestId + " is not approved or status is null. Rejecting. Thread: " + Thread.currentThread().getName());
//            throw new RuntimeException("Rent Request must be approved to create a payment intent.");
//        }
//        if (!rentRequest.getRenter().getId().equals(renter.getId())) {
//            System.out.println("PaymentService: Renter mismatch for Rent Request ID " + rentRequestId + ". Expected: " + rentRequest.getRenter().getId() + ", Actual: " + renter.getId() + ". Thread: " + Thread.currentThread().getName());
//            throw new RuntimeException("You do not have permission to create a payment for this rent request.");
//        }
//        if (!rentRequest.getProperty().getId().equals(propertyId)) {
//            System.out.println("PaymentService: Property ID mismatch for Rent Request ID " + rentRequestId + ". Expected: " + rentRequest.getProperty().getId() + ", Actual: " + propertyId + ". Thread: " + Thread.currentThread().getName());
//            throw new RuntimeException("Property ID mismatch for the given rent request. Please ensure correct data.");
//        }
//        if (property.getOwner() == null) {
//            System.out.println("PaymentService: Property ID " + propertyId + " does not have an associated owner. Thread: " + Thread.currentThread().getName());
//            throw new RuntimeException("Property does not have an associated owner.");
//        }
//
//        // 3. Check for existing payment for this Rent Request ID
//        System.out.println("PaymentService: Checking for existing payment record for Rent Request ID: " + rentRequestId + " on thread: " + Thread.currentThread().getName());
//        Optional<Payment> existingPaymentOptional = paymentRepository.findByRentRequestId(rentRequestId);
//
//        if (existingPaymentOptional.isPresent()) {
//            Payment existingPayment = existingPaymentOptional.get();
//            System.out.println("PaymentService: Found EXISTING payment record for Rent Request ID " + rentRequestId +
//                    ". DB Payment ID: " + existingPayment.getId() +
//                    ", Stripe Intent ID: " + existingPayment.getStripePaymentIntentId() +
//                    ", Status: " + existingPayment.getStatus() + ". Thread: " + Thread.currentThread().getName());
//
//            if ("succeeded".equals(existingPayment.getStatus())) {
//                System.out.println("PaymentService: Payment for Rent Request ID " + rentRequestId + " is already SUCCEEDED. Rejecting new intent. Thread: " + Thread.currentThread().getName());
//                throw new RuntimeException("Payment has already been successfully completed for this rent request.");
//            }
//
//            // If payment exists but not succeeded, retrieve and return its Stripe Intent
//            System.out.println("PaymentService: Existing payment found for Rent Request ID " + rentRequestId +
//                    " is not succeeded. Retrieving and returning existing Stripe PaymentIntent. Thread: " + Thread.currentThread().getName());
//            return stripeService.retrievePaymentIntent(existingPayment.getStripePaymentIntentId());
//        }
//
//        System.out.println("PaymentService: NO EXISTING payment record found for Rent Request ID: " + rentRequestId + ". Proceeding to create new Stripe PaymentIntent. Thread: " + Thread.currentThread().getName());
//
//        // If no existing payment found for this rent request, proceed to create a new one
//        Long amountInCents = amount.multiply(new BigDecimal("100")).longValue();
//
//        System.out.println("PaymentService: Creating NEW Stripe PaymentIntent for amount: " + amountInCents + " " + currency + ". Thread: " + Thread.currentThread().getName());
//        PaymentIntent paymentIntent = stripeService.createPaymentIntent(amountInCents, currency, description);
//
//        Payment payment = new Payment();
//        payment.setStripePaymentIntentId(paymentIntent.getId());
//        payment.setAmount(amount);
//        payment.setCurrency(paymentIntent.getCurrency());
//        payment.setStatus(paymentIntent.getStatus());
//        payment.setRenter(renter);
//        payment.setOwner(property.getOwner());
//        payment.setRentRequest(rentRequest);
//        payment.setPaymentDate(LocalDateTime.now());
//
//        System.out.println("PaymentService: Attempting to save NEW Payment record to DB for Rent Request ID: " + rentRequestId +
//                " with Stripe Intent ID: " + paymentIntent.getId() + ". Thread: " + Thread.currentThread().getName());
//        paymentRepository.save(payment);
//
//        System.out.println("PaymentService: Successfully saved NEW Payment record to DB for Rent Request ID: " + rentRequestId + ". Thread: " + Thread.currentThread().getName());
//        return paymentIntent;
//    }
//
//    @Transactional
//    public void updatePaymentStatus(String paymentIntentId, String status, Long rentRequestId, User authenticatedRenter) {
//        System.out.println("PaymentService: Entering updatePaymentStatus for Stripe Intent ID: " + paymentIntentId + ", Status: " + status + ", Rent Request ID: " + rentRequestId + ". Thread: " + Thread.currentThread().getName());
//        Payment payment = paymentRepository.findByStripePaymentIntentId(paymentIntentId)
//                .orElseThrow(() -> {
//                    System.out.println("PaymentService: Payment record not found for Stripe Intent ID: " + paymentIntentId);
//                    return new RuntimeException("Payment record not found for Stripe Intent ID: " + paymentIntentId);
//                });
//        System.out.println("PaymentService: Found payment record for update. Current status: " + payment.getStatus() + ". Thread: " + Thread.currentThread().getName());
//
//        if (!payment.getRenter().getId().equals(authenticatedRenter.getId())) {
//            System.out.println("PaymentService: Unauthorized attempt to update payment. Renter ID mismatch. Thread: " + Thread.currentThread().getName());
//            throw new RuntimeException("Unauthorized: You do not have permission to update this payment status.");
//        }
//        if (rentRequestId != null && !payment.getRentRequest().getId().equals(rentRequestId)) {
//            System.out.println("PaymentService: Rent Request ID mismatch during update for Stripe Intent ID: " + paymentIntentId + ". Expected: " + payment.getRentRequest().getId() + ", Actual: " + rentRequestId + ". Thread: " + Thread.currentThread().getName());
//            throw new RuntimeException("Rent Request ID mismatch for the payment confirmation. Data integrity error.");
//        }
//
//        payment.setStatus(status);
//        payment.setLastUpdatedDate(LocalDateTime.now());
//        paymentRepository.save(payment);
//        System.out.println("PaymentService: Payment status updated to '" + status + "' for intent " + paymentIntentId + " in DB. Thread: " + Thread.currentThread().getName());
//    }
//
//    public String getStripePublishableKey() {
//        return stripeService.getPublishableKey();
//    }
//} jun 14 7:55 working code

//@Service
//@RequiredArgsConstructor
//public class PaymentService {
//
//    private final StripeService stripeService;
//    private final PaymentRepository paymentRepository;
//    private final RentRequestRepository rentRequestRepository;
//    private final PropertyRepository propertyRepository;
//
//    @Transactional
//    public PaymentIntent createPaymentIntent(
//            BigDecimal amount,
//            String currency,
//            String description,
//            Long rentRequestId,
//            Long propertyId,
//            User renter
//    ) throws StripeException {
//        System.out.println("PaymentService: Entering createPaymentIntent for Rent Request ID: " + rentRequestId + " on thread: " + Thread.currentThread().getName());
//
//        // 1. Check if Rent Request exists
//        RentRequest rentRequest = rentRequestRepository.findById(rentRequestId)
//                .orElseThrow(() -> {
//                    System.out.println("PaymentService: Rent Request not found with ID: " + rentRequestId);
//                    return new RuntimeException("Rent Request not found with ID: " + rentRequestId);
//                });
//        System.out.println("PaymentService: Found Rent Request ID: " + rentRequest.getId() + ", Approved: " + rentRequest.getIsApproved() + " on thread: " + Thread.currentThread().getName());
//
//
//        // 2. Check if Property exists (this is important for later logic)
//        Property property = propertyRepository.findById(propertyId)
//                .orElseThrow(() -> {
//                    System.out.println("PaymentService: Property not found with ID: " + propertyId);
//                    return new RuntimeException("Property not found with ID: " + propertyId);
//                });
//        System.out.println("PaymentService: Found Property ID: " + property.getId() + " on thread: " + Thread.currentThread().getName());
//
//        if (rentRequest.getIsApproved() == null || !rentRequest.getIsApproved()) {
//            System.out.println("PaymentService: Rent Request ID " + rentRequestId + " is not approved or status is null. Rejecting. Thread: " + Thread.currentThread().getName());
//            throw new RuntimeException("Rent Request must be approved to create a payment intent.");
//        }
//        if (!rentRequest.getRenter().getId().equals(renter.getId())) {
//            System.out.println("PaymentService: Renter mismatch for Rent Request ID " + rentRequestId + ". Expected: " + rentRequest.getRenter().getId() + ", Actual: " + renter.getId() + ". Thread: " + Thread.currentThread().getName());
//            throw new RuntimeException("You do not have permission to create a payment for this rent request.");
//        }
//        if (!rentRequest.getProperty().getId().equals(propertyId)) {
//            System.out.println("PaymentService: Property ID mismatch for Rent Request ID " + rentRequestId + ". Expected: " + rentRequest.getProperty().getId() + ", Actual: " + propertyId + ". Thread: " + Thread.currentThread().getName());
//            throw new RuntimeException("Property ID mismatch for the given rent request. Please ensure correct data.");
//        }
//        if (property.getOwner() == null) {
//            System.out.println("PaymentService: Property ID " + propertyId + " does not have an associated owner. Thread: " + Thread.currentThread().getName());
//            throw new RuntimeException("Property does not have an associated owner.");
//        }
//
//        // 3. Check for existing payment for this Rent Request ID
//        System.out.println("PaymentService: Checking for existing payment record for Rent Request ID: " + rentRequestId + " on thread: " + Thread.currentThread().getName());
//        Optional<Payment> existingPaymentOptional = paymentRepository.findByRentRequestId(rentRequestId);
//
//        if (existingPaymentOptional.isPresent()) {
//            Payment existingPayment = existingPaymentOptional.get();
//            System.out.println("PaymentService: Found EXISTING payment record for Rent Request ID " + rentRequestId +
//                    ". DB Payment ID: " + existingPayment.getId() +
//                    ", Stripe Intent ID: " + existingPayment.getStripePaymentIntentId() +
//                    ", Status: " + existingPayment.getStatus() + ". Thread: " + Thread.currentThread().getName());
//
//            if ("succeeded".equals(existingPayment.getStatus())) {
//                System.out.println("PaymentService: Payment for Rent Request ID " + rentRequestId + " is already SUCCEEDED. Rejecting new intent. Thread: " + Thread.currentThread().getName());
//                throw new RuntimeException("Payment has already been successfully completed for this rent request.");
//            }
//
//            // If payment exists but not succeeded, retrieve and return its Stripe Intent
//            System.out.println("PaymentService: Existing payment found for Rent Request ID " + rentRequestId +
//                    " is not succeeded. Retrieving and returning existing Stripe PaymentIntent. Thread: " + Thread.currentThread().getName());
//            return stripeService.retrievePaymentIntent(existingPayment.getStripePaymentIntentId());
//        }
//
//        System.out.println("PaymentService: NO EXISTING payment record found for Rent Request ID: " + rentRequestId + ". Proceeding to create new Stripe PaymentIntent. Thread: " + Thread.currentThread().getName());
//
//        // If no existing payment found for this rent request, proceed to create a new one
//        Long amountInCents = amount.multiply(new BigDecimal("100")).longValue();
//
//        System.out.println("PaymentService: Creating NEW Stripe PaymentIntent for amount: " + amountInCents + " " + currency + ". Thread: " + Thread.currentThread().getName());
//        PaymentIntent paymentIntent = stripeService.createPaymentIntent(amountInCents, currency, description);
//
//        Payment payment = new Payment();
//        payment.setStripePaymentIntentId(paymentIntent.getId());
//        payment.setAmount(amount);
//        payment.setCurrency(paymentIntent.getCurrency());
//        payment.setStatus(paymentIntent.getStatus());
//        payment.setRenter(renter);
//        payment.setOwner(property.getOwner());
//        payment.setRentRequest(rentRequest);
//        payment.setPaymentDate(LocalDateTime.now());
//
//        System.out.println("PaymentService: Attempting to save NEW Payment record to DB for Rent Request ID: " + rentRequestId +
//                " with Stripe Intent ID: " + paymentIntent.getId() + ". Thread: " + Thread.currentThread().getName());
//        paymentRepository.save(payment);
//
//        System.out.println("PaymentService: Successfully saved NEW Payment record to DB for Rent Request ID: " + rentRequestId + ". Thread: " + Thread.currentThread().getName());
//        return paymentIntent;
//    }
//
//    @Transactional
//    public void updatePaymentStatus(String paymentIntentId, String status, Long rentRequestId, User authenticatedRenter) {
//        System.out.println("PaymentService: Entering updatePaymentStatus for Stripe Intent ID: " + paymentIntentId + ", Status: " + status + ", Rent Request ID: " + rentRequestId + ". Thread: " + Thread.currentThread().getName());
//        Payment payment = paymentRepository.findByStripePaymentIntentId(paymentIntentId)
//                .orElseThrow(() -> {
//                    System.out.println("PaymentService: Payment record not found for Stripe Intent ID: " + paymentIntentId);
//                    return new RuntimeException("Payment record not found for Stripe Intent ID: " + paymentIntentId);
//                });
//        System.out.println("PaymentService: Found payment record for update. Current status: " + payment.getStatus() + ". Thread: " + Thread.currentThread().getName());
//
//        if (!payment.getRenter().getId().equals(authenticatedRenter.getId())) {
//            System.out.println("PaymentService: Unauthorized attempt to update payment. Renter ID mismatch. Thread: " + Thread.currentThread().getName());
//            throw new RuntimeException("Unauthorized: You do not have permission to update this payment status.");
//        }
//        if (rentRequestId != null && !payment.getRentRequest().getId().equals(rentRequestId)) {
//            System.out.println("PaymentService: Rent Request ID mismatch during update for Stripe Intent ID: " + paymentIntentId + ". Expected: " + payment.getRentRequest().getId() + ", Actual: " + rentRequestId + ". Thread: " + Thread.currentThread().getName());
//            throw new RuntimeException("Rent Request ID mismatch for the payment confirmation. Data integrity error.");
//        }
//
//        payment.setStatus(status);
//        payment.setLastUpdatedDate(LocalDateTime.now());
//        paymentRepository.save(payment);
//        System.out.println("PaymentService: Payment status updated to '" + status + "' for intent " + paymentIntentId + " in DB. Thread: " + Thread.currentThread().getName());
//    }
//
//    public String getStripePublishableKey() {
//        return stripeService.getPublishableKey();
//    }
//}
//

//jun 23 final code

import com.edelala.mur.entity.Payment;
import com.edelala.mur.entity.RentRequest; // Import RentRequest
import com.edelala.mur.entity.User; // Import User
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);

    private final PaymentRepository paymentRepository;
    private final RentRequestService rentRequestService;
    private final UserService userService;

    @Value("${stripe.api.secret-key}")
    private String stripeSecretKey;

    @Value("${stripe.api.publishable-key}")
    private String stripePublishableKey;

    public String getStripePublishableKey() {
        return stripePublishableKey;
    }

    /**
     * Creates or retrieves a Stripe PaymentIntent and a corresponding Payment record in the DB.
     *
     * @param amount The amount to pay.
     * @param currency The currency (e.g., "usd").
     * @param description A description for the payment.
     * @param rentRequestId The ID of the associated RentRequest.
     * @param propertyId The ID of the associated Property.
     * @param authenticatedUser The authenticated Renter.
     * @return The Stripe PaymentIntent object.
     * @throws StripeException If there's an error with the Stripe API.
     * @throws RuntimeException If rent request or user not found, or not authorized.
     */
    @Transactional
    public PaymentIntent createPaymentIntent(BigDecimal amount, String currency, String description,
                                             Long rentRequestId, Long propertyId, User authenticatedUser) throws StripeException {
        Stripe.apiKey = stripeSecretKey;

        logger.debug("PaymentService: Attempting to create/retrieve payment intent for rentRequestId: {}", rentRequestId);

        // Fetch rent request to validate and get accurate details
        RentRequest rentRequest = rentRequestService.getRentRequestById(rentRequestId)
                .orElseThrow(() -> new RuntimeException("Rent Request not found for ID: " + rentRequestId));

        // Basic authorization check: Ensure the authenticated user is the renter for this request
        if (!rentRequest.getRenter().getId().equals(authenticatedUser.getId())) {
            throw new RuntimeException("Authenticated user is not the renter for this request.");
        }

        // Check if a Payment record already exists for this rent request with 'requires_payment_method' status
        Optional<Payment> existingPaymentRecord = paymentRepository.findByRentRequestIdAndStatus(rentRequestId, "requires_payment_method");

        PaymentIntent intent;
        Payment paymentRecord;

        if (existingPaymentRecord.isPresent()) {
            paymentRecord = existingPaymentRecord.get();
            // Retrieve the existing PaymentIntent from Stripe using its ID
            intent = PaymentIntent.retrieve(paymentRecord.getStripePaymentIntentId());
            logger.info("PaymentService: Reusing existing Stripe PaymentIntent ID: {}", intent.getId());
        } else {
            // Create a new Stripe PaymentIntent
            PaymentIntentCreateParams createParams =
                    PaymentIntentCreateParams.builder()
                            .setAmount(amount.multiply(new BigDecimal("100")).longValueExact()) // Amount in cents
                            .setCurrency(currency)
                            .setDescription(description)
                            .setAutomaticPaymentMethods(
                                    PaymentIntentCreateParams.AutomaticPaymentMethods.builder().setEnabled(true).build()
                            )
                            .build();
            intent = PaymentIntent.create(createParams);
            logger.info("PaymentService: Created new Stripe PaymentIntent ID: {}", intent.getId());

            // Create and save a new Payment record in your database
            paymentRecord = Payment.builder()
                    .stripePaymentIntentId(intent.getId())
                    .amount(amount)
                    .currency(currency)
                    .description(description)
                    .status(intent.getStatus()) // Initial status from Stripe
                    .paymentDate(LocalDateTime.now()) // Set initial payment date on creation
                    .renter(authenticatedUser) // Set the renter
                    .owner(rentRequest.getProperty().getOwner()) // Set the property owner
                    .rentRequest(rentRequest) // Set the RentRequest object
                    .build();
            paymentRepository.save(paymentRecord);
            logger.debug("PaymentService: Saved new Payment record to DB with ID: {}", paymentRecord.getId());
        }

        // Update the RentRequest's payment status in your DB if it's not already set
        if (!"requires_payment_method".equalsIgnoreCase(rentRequest.getPaymentStatus())) {
            rentRequestService.updatePaymentStatus(rentRequest.getId(), intent.getStatus());
            logger.debug("PaymentService: Updated RentRequest {} payment status to {}", rentRequestId, intent.getStatus());
        }

        return intent;
    }


    /**
     * Updates the status of a Payment record and the associated RentRequest.
     *
     * @param paymentIntentId The Stripe Payment Intent ID.
     * @param newStatus The new status (e.g., "succeeded", "failed").
     * @param rentRequestId The ID of the associated RentRequest.
     * @param authenticatedUser The authenticated user (Renter).
     * @throws RuntimeException if payment or rent request not found, or not authorized.
     */
    @Transactional
    public void updatePaymentStatus(String paymentIntentId, String newStatus, Long rentRequestId, User authenticatedUser) {
        logger.debug("PaymentService: Attempting to update payment status for intent: {}, newStatus: {}, rentRequestId: {}", paymentIntentId, newStatus, rentRequestId);

        Payment payment = paymentRepository.findByStripePaymentIntentId(paymentIntentId)
                .orElseThrow(() -> new RuntimeException("Payment record not found for intent ID: " + paymentIntentId));

        RentRequest rentRequest = rentRequestService.getRentRequestById(rentRequestId)
                .orElseThrow(() -> new RuntimeException("Rent Request not found for ID: " + rentRequestId));

        if (!rentRequest.getRenter().getId().equals(authenticatedUser.getId())) {
            throw new RuntimeException("Authenticated user is not authorized to confirm payment for this request.");
        }

        if ("succeeded".equalsIgnoreCase(newStatus) && !"PAID".equalsIgnoreCase(payment.getStatus())) {
            payment.setStatus("PAID");
            payment.setPaymentDate(LocalDateTime.now());
            logger.info("PaymentService: Payment record for intent {} status updated to 'PAID'.", paymentIntentId);

            rentRequestService.updatePaymentStatus(rentRequestId, "PAID"); // Use "PAID"
            logger.info("PaymentService: RentRequest {} payment status updated to 'PAID'.", rentRequestId);
        } else if ("failed".equalsIgnoreCase(newStatus)) {
            payment.setStatus("FAILED");
            logger.info("PaymentService: Payment record for intent {} status updated to 'FAILED'.", paymentIntentId);
            rentRequestService.updatePaymentStatus(rentRequestId, "FAILED"); // Use "FAILED"
            logger.info("PaymentService: RentRequest {} payment status updated to 'FAILED'.", rentRequestId);
        } else {
            // Handle other Stripe statuses like 'requires_action', 'requires_capture', etc.
            payment.setStatus(newStatus.toUpperCase());
            logger.info("PaymentService: Payment record for intent {} status updated to '{}'.", paymentIntentId, newStatus.toUpperCase());
            rentRequestService.updatePaymentStatus(rentRequestId, newStatus.toUpperCase()); // <--- FIXED: Used newStatus here
            logger.info("PaymentService: RentRequest {} payment status updated to '{}'.", rentRequestId, newStatus.toUpperCase());
        }

        paymentRepository.save(payment);
        logger.debug("PaymentService: Payment record saved to DB.");
    }

    public Optional<Payment> findByRentRequestIdAndStatus(Long rentRequestId, String status) {
        return paymentRepository.findByRentRequestIdAndStatus(rentRequestId, status);
    }

    public Optional<Payment> findByStripePaymentIntentId(String stripePaymentIntentId) {
        return paymentRepository.findByStripePaymentIntentId(stripePaymentIntentId);
    }
}