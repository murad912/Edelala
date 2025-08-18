package com.edelala.mur.controller;


import com.edelala.mur.dto.PaymentIntentConfirmRequest;
import com.edelala.mur.dto.PaymentIntentCreateRequest;
import com.edelala.mur.dto.PaymentIntentResponse;
import com.edelala.mur.entity.Payment;
import com.edelala.mur.entity.RentRequest;
import com.edelala.mur.entity.User;
import com.edelala.mur.service.PaymentService;
import com.edelala.mur.service.RentRequestService;
import com.edelala.mur.service.UserService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

//@RestController
//@RequestMapping("/api/payments")
//@RequiredArgsConstructor
//public class PaymentController {
//
//    private final PaymentService paymentService;
//    private final UserService userService; // Inject UserService to get the current user
//
//    @Value("${broker.service.fee}")
//    private BigDecimal brokerServiceFee;
//
//    // Re-inject the publishable key, as frontend will need it
//    @Value("${stripe.api.publishable-key}")
//    private String stripePublishableKey;
//
//    /**
//     * Endpoint to create a Stripe Payment Intent for the broker service fee.
//     * This is called by the frontend to initiate a payment.
//     * It requires a RENTER role.
//     *
//     * @param request Contains rentRequestId (and potentially other payment context later).
//     * @param userDetails The authenticated user (renter).
//     * @return A Map containing the client secret and publishable key.
//     */
//    @PostMapping("/create-payment-intent")
//    @PreAuthorize("hasRole('RENTER')")
//    public ResponseEntity<Map<String, String>> createPaymentIntent(
//            @Valid @RequestBody PaymentIntentCreateRequest request, // Use DTO to pass rentRequestId
//            @AuthenticationPrincipal UserDetails userDetails
//    ) {
//        try {
//            User renter = userService.findByEmail(userDetails.getUsername())
//                    .orElseThrow(() -> new RuntimeException("Renter not found for authenticated user."));
//
//            // Use the brokerServiceFee as the amount
//            PaymentIntent paymentIntent = paymentService.createPaymentIntent(
//                    brokerServiceFee,
//                    "Broker service fee for Rent Request ID: " + request.getRentRequestId(),
//                    request.getRentRequestId(), // Pass rentRequestId to the service
//                    renter // Pass renter to the service
//            );
//
//            Map<String, String> response = new HashMap<>();
//            response.put("clientSecret", paymentIntent.getClientSecret());
//            response.put("publishableKey", stripePublishableKey); // Include publishable key
//            return ResponseEntity.ok(response);
//        } catch (StripeException e) {
//            System.err.println("Stripe API Error: " + e.getMessage());
//            // Return error response with HTTP status
//            Map<String, String> errorResponse = new HashMap<>();
//            errorResponse.put("message", "Stripe payment intent creation failed: " + e.getMessage());
//            errorResponse.put("publishableKey", stripePublishableKey); // Still return publishable key
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
//        } catch (RuntimeException e) {
//            System.err.println("Application Logic Error: " + e.getMessage());
//            // Return error response with HTTP status
//            Map<String, String> errorResponse = new HashMap<>();
//            errorResponse.put("message", e.getMessage());
//            errorResponse.put("publishableKey", stripePublishableKey);
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
//        }
//    }
//
//    /**
//     * Endpoint to confirm a payment status from the frontend or a webhook.
//     * This should typically be a POST from client after Stripe confirms payment.
//     * For full robustness, a Stripe webhook would be better for final status updates.
//     *
//     * @param payload Map containing paymentIntentId and status (e.g., "succeeded").
//     * @return 200 OK if successful.
//     */
//    @PostMapping("/confirm-payment")
//    @PreAuthorize("hasRole('RENTER')") // Can be adjusted if a webhook is unauthenticated
//    public ResponseEntity<String> confirmPayment(
//            @RequestBody Map<String, String> payload
//    ) {
//        String paymentIntentId = payload.get("paymentIntentId");
//        String status = payload.get("status"); // Expected "succeeded", "failed", "canceled"
//
//        if (paymentIntentId == null || status == null) {
//            return new ResponseEntity<>("Missing paymentIntentId or status.", HttpStatus.BAD_REQUEST);
//        }
//
//        try {
//            // Update the payment record in your database
//            paymentService.updatePaymentStatus(paymentIntentId, status);
//            return ResponseEntity.ok("Payment status updated successfully.");
//        } catch (RuntimeException e) {
//            System.err.println("Error confirming payment: " + e.getMessage());
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//    }
//} Jun 12 modify to fix payment issue

//@RestController
//@RequestMapping("/api/payments")
//@RequiredArgsConstructor
//public class PaymentController {
//
//    private final PaymentService paymentService;
//    private final UserService userService;
//
//    // We no longer need brokerServiceFee here, as amount comes from frontend
//    // @Value("${broker.service.fee}")
//    // private BigDecimal brokerServiceFee;
//
//    // Publishable key is now returned by PaymentIntentResponse via StripeService
//    // @Value("${stripe.api.publishable-key}")
//    // private String stripePublishableKey;
//
//    /**
//     * Endpoint to create a Stripe Payment Intent.
//     * This is called by the frontend to initiate a payment.
//     * It requires a RENTER role.
//     *
//     * @param request Contains amount, currency, propertyId, and rentRequestId.
//     * @param userDetails The authenticated user (renter).
//     * @return A Map containing the client secret and publishable key.
//     */
//    @PostMapping("/create-payment-intent")
//    @PreAuthorize("hasRole('RENTER')")
//    public ResponseEntity<PaymentIntentResponse> createPaymentIntent(
//            @Valid @RequestBody PaymentIntentCreateRequest request,
//            @AuthenticationPrincipal UserDetails userDetails
//    ) {
//        try {
//            User renter = userService.findByEmail(userDetails.getUsername())
//                    .orElseThrow(() -> new RuntimeException("Renter not found for authenticated user."));
//
//            // Use the amount and currency from the request DTO
//            PaymentIntent paymentIntent = paymentService.createPaymentIntent(
//                    request.getAmount(),
//                    request.getCurrency(),
//                    "Rent payment for Property ID: " + request.getPropertyId() + ", Rent Request ID: " + request.getRentRequestId(),
//                    request.getRentRequestId(),
//                    request.getPropertyId(), // Pass propertyId
//                    renter
//            );
//
//            // Fetch the publishable key from StripeService
//            String publishableKey = paymentService.getStripePublishableKey(); // New method in PaymentService
//
//            PaymentIntentResponse response = new PaymentIntentResponse(
//                    paymentIntent.getClientSecret(),
//                    publishableKey
//            );
//            return ResponseEntity.ok(response);
//        } catch (StripeException e) {
//            System.err.println("Stripe API Error: " + e.getMessage());
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(new PaymentIntentResponse(null, paymentService.getStripePublishableKey())); // Return publishable key even on error
//        } catch (RuntimeException e) {
//            System.err.println("Application Logic Error: " + e.getMessage());
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                    .body(new PaymentIntentResponse(null, paymentService.getStripePublishableKey())); // Return publishable key even on error
//        }
//    }
//
//    /**
//     * Endpoint to confirm a payment status from the frontend or a webhook.
//     * This should typically be a POST from client after Stripe confirms payment.
//     * For full robustness, a Stripe webhook would be better for final status updates.
//     *
//     * @param payload Map containing paymentIntentId, status (e.g., "succeeded"), and optionally rentRequestId.
//     * @return 200 OK if successful.
//     */
//    @PostMapping("/confirm-payment")
//    @PreAuthorize("hasRole('RENTER')") // Can be adjusted if a webhook is unauthenticated
//    public ResponseEntity<String> confirmPayment(
//            @RequestBody Map<String, String> payload,
//            @AuthenticationPrincipal UserDetails userDetails // Added to link to authenticated user
//    ) {
//        String paymentIntentId = payload.get("paymentIntentId");
//        String status = payload.get("status");
//        Long rentRequestId = payload.containsKey("rentRequestId") ? Long.parseLong(payload.get("rentRequestId")) : null;
//
//
//        if (paymentIntentId == null || status == null) {
//            return new ResponseEntity<>("Missing paymentIntentId or status.", HttpStatus.BAD_REQUEST);
//        }
//
//        try {
//            User renter = userService.findByEmail(userDetails.getUsername())
//                    .orElseThrow(() -> new RuntimeException("Renter not found for authenticated user."));
//            // Update the payment record in your database
//            paymentService.updatePaymentStatus(paymentIntentId, status, rentRequestId, renter);
//            return ResponseEntity.ok("Payment status updated successfully.");
//        } catch (RuntimeException e) {
//            System.err.println("Error confirming payment: " + e.getMessage());
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//    }
//}
// jun13

//@RestController
//@RequestMapping("/api/payments")
//@RequiredArgsConstructor
//public class PaymentController {
//
//    private final PaymentService paymentService;
//    private final UserService userService;
//
//    /**
//     * Endpoint to create a Stripe Payment Intent.
//     * This is called by the frontend to initiate a payment.
//     * It requires a RENTER role.
//     *
//     * @param request Contains amount, currency, propertyId, and rentRequestId.
//     * @param userDetails The authenticated user (renter).
//     * @return A Map containing the client secret and publishable key.
//     */
//    @PostMapping("/create-payment-intent")
//    @PreAuthorize("hasRole('RENTER')")
//    public ResponseEntity<PaymentIntentResponse> createPaymentIntent(
//            @Valid @RequestBody PaymentIntentCreateRequest request,
//            @AuthenticationPrincipal UserDetails userDetails
//    ) {
//        // FIX: Add logging for authenticated user and roles
//        System.out.println("PaymentController Debug: createPaymentIntent called.");
//        System.out.println("PaymentController Debug: Authenticated User: " + userDetails.getUsername());
//        System.out.println("PaymentController Debug: User Roles: " + userDetails.getAuthorities()); // Should show RENTER
//
//        try {
//            User renter = userService.findByEmail(userDetails.getUsername())
//                    .orElseThrow(() -> new RuntimeException("Renter not found for authenticated user."));
//
//            PaymentIntent paymentIntent = paymentService.createPaymentIntent(
//                    request.getAmount(),
//                    request.getCurrency(),
//                    "Rent payment for Property ID: " + request.getPropertyId() + ", Rent Request ID: " + request.getRentRequestId(),
//                    request.getRentRequestId(),
//                    request.getPropertyId(),
//                    renter
//            );
//
//            String publishableKey = paymentService.getStripePublishableKey();
//
//            PaymentIntentResponse response = new PaymentIntentResponse(
//                    paymentIntent.getClientSecret(),
//                    publishableKey
//            );
//            return ResponseEntity.ok(response);
//        } catch (StripeException e) {
//            System.err.println("PaymentController Stripe API Error: " + e.getMessage());
//            // Return error response with HTTP status
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(new PaymentIntentResponse(null, paymentService.getStripePublishableKey())); // Return publishable key even on error
//        } catch (RuntimeException e) {
//            System.err.println("PaymentController Application Logic Error: " + e.getMessage());
//            // Return error response with HTTP status
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                    .body(new PaymentIntentResponse(null, paymentService.getStripePublishableKey())); // Return publishable key even on error
//        }
//    }
//
//    /**
//     * Endpoint to confirm a payment status from the frontend or a webhook.
//     *
//     * @param payload Map containing paymentIntentId, status (e.g., "succeeded"), and optionally rentRequestId.
//     * @return 200 OK if successful.
//     */
//    @PostMapping("/confirm-payment")
//    @PreAuthorize("hasRole('RENTER')")
//    public ResponseEntity<String> confirmPayment(
//            @RequestBody Map<String, String> payload,
//            @AuthenticationPrincipal UserDetails userDetails
//    ) {
//        System.out.println("PaymentController Debug: confirmPayment called.");
//        System.out.println("PaymentController Debug: Authenticated User: " + userDetails.getUsername());
//        System.out.println("PaymentController Debug: User Roles: " + userDetails.getAuthorities());
//
//        String paymentIntentId = payload.get("paymentIntentId");
//        String status = payload.get("status");
//        Long rentRequestId = payload.containsKey("rentRequestId") ? Long.parseLong(payload.get("rentRequestId")) : null; // Ensure rentRequestId is handled
//
//        if (paymentIntentId == null || status == null) {
//            return new ResponseEntity<>("Missing paymentIntentId or status.", HttpStatus.BAD_REQUEST);
//        }
//
//        try {
//            User renter = userService.findByEmail(userDetails.getUsername())
//                    .orElseThrow(() -> new RuntimeException("Renter not found for authenticated user."));
//            paymentService.updatePaymentStatus(paymentIntentId, status, rentRequestId, renter);
//            return ResponseEntity.ok("Payment status updated successfully.");
//        } catch (RuntimeException e) {
//            System.err.println("Error confirming payment: " + e.getMessage());
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//    }
//}
// jun 13 10:03pm

//@RestController
//@RequestMapping("/api/payments")
//@RequiredArgsConstructor
//public class PaymentController {
//
//    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);
//
//    private final PaymentService paymentService;
//
//    @PostMapping("/create-payment-intent")
//    public ResponseEntity<?> createPaymentIntent(
//            @RequestBody PaymentIntentCreateRequest request,
//            @AuthenticationPrincipal User authenticatedUser
//    ) {
//        logger.debug("PaymentController Debug: createPaymentIntent called.");
//        if (authenticatedUser == null) {
//            logger.warn("PaymentController: Unauthenticated user attempt to create payment intent.");
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated.");
//        }
//
//        logger.debug("PaymentController Debug: Authenticated User: {}", authenticatedUser.getEmail());
//        logger.debug("PaymentController Debug: User Roles: {}", authenticatedUser.getAuthorities());
//        logger.debug("PaymentController Debug: Received request payload: {}", request);
//
//
//        // Basic validation for input
//        if (request.getRentRequestId() == null || request.getAmount() == null ||
//                request.getCurrency() == null || request.getPropertyId() == null) {
//            logger.warn("PaymentController: Missing required fields in payment intent creation request. Payload: {}", request);
//            return ResponseEntity.badRequest().body("Missing required payment details (rentRequestId, amount, currency, propertyId).");
//        }
//
//        try {
//            PaymentIntent paymentIntent = paymentService.createPaymentIntent(
//                    request.getAmount(),
//                    request.getCurrency(),
//                    "Rent payment for property ID: " + request.getPropertyId(), // Description for Stripe
//                    request.getRentRequestId(),
//                    request.getPropertyId(),
//                    authenticatedUser
//            );
//
//            logger.debug("PaymentController Debug: Stripe PaymentIntent created/retrieved. ID: {}, Status: {}", paymentIntent.getId(), paymentIntent.getStatus());
//            logger.debug("PaymentController Debug: Client Secret (from Stripe): {}", paymentIntent.getClientSecret() != null ? "Present" : "Missing");
//
//            // Construct the DTO response for the frontend
//            PaymentIntentResponse response = new PaymentIntentResponse(
//                    paymentIntent.getClientSecret(),
//                    paymentService.getStripePublishableKey(), // Get publishable key from service
//                    paymentIntent.getStatus()
//            );
//
//            return ResponseEntity.ok(response);
//
//        } catch (StripeException e) {
//            logger.error("PaymentController: Stripe API Error during createPaymentIntent: {}", e.getMessage(), e);
//            // Provide a generic error message to the frontend, log full details on backend
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("Failed to process payment with Stripe. Please try again or contact support.");
//        } catch (RuntimeException e) {
//            logger.error("PaymentController: Application Logic Error during createPaymentIntent: {}", e.getMessage(), e);
//            // This catches your custom exceptions (e.g., rent request not approved, duplicate payment)
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST) // Use 400 for application logic errors
//                    .body(e.getMessage());
//        } catch (Exception e) {
//            logger.error("PaymentController: Unexpected error during createPaymentIntent: {}", e.getMessage(), e);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("An unexpected error occurred. Please try again.");
//        }
//    }
//
//    @PostMapping("/confirm-payment")
//    public ResponseEntity<?> confirmPayment(
//            @RequestBody PaymentIntentConfirmRequest request,
//            @AuthenticationPrincipal User authenticatedUser
//    ) {
//        logger.debug("PaymentController Debug: confirmPayment called.");
//        if (authenticatedUser == null) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated.");
//        }
//        logger.debug("PaymentController Debug: Authenticated User for confirmPayment: {}", authenticatedUser.getEmail());
//
//        // Basic validation for input
//        if (request.getPaymentIntentId() == null || request.getStatus() == null || request.getRentRequestId() == null) {
//            logger.warn("PaymentController: Missing required fields for payment confirmation.");
//            return ResponseEntity.badRequest().body("Missing required confirmation details (paymentIntentId, status, rentRequestId).");
//        }
//
//        try {
//            paymentService.updatePaymentStatus(
//                    request.getPaymentIntentId(),
//                    request.getStatus(),
//                    request.getRentRequestId(),
//                    authenticatedUser
//            );
//            logger.info("PaymentController: Payment intent {} status updated to {}", request.getPaymentIntentId(), request.getStatus());
//            return ResponseEntity.ok("Payment status updated successfully.");
//        } catch (RuntimeException e) {
//            logger.error("PaymentController: Application Logic Error during confirmPayment: {}", e.getMessage(), e);
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                    .body(e.getMessage());
//        } catch (Exception e) {
//            logger.error("PaymentController: Unexpected error during confirmPayment: {}", e.getMessage(), e);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("An unexpected error occurred during payment confirmation.");
//        }
//    }
//} jun 13 10:31

//@RestController
//@RequestMapping("/api/payments")
//@RequiredArgsConstructor
//public class PaymentController {
//
//    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);
//
//    private final PaymentService paymentService;
//
//    /**
//     * Endpoint to create a Stripe Payment Intent.
//     * This is called by the frontend to initiate a payment.
//     * It requires a RENTER role.
//     *
//     * @param request Contains amount, currency, propertyId, and rentRequestId.
//     * @param authenticatedUser The authenticated user (renter).
//     * @return A DTO containing the client secret and publishable key.
//     */
//    @PostMapping("/create-payment-intent")
//    @PreAuthorize("hasRole('RENTER')") // Ensure this annotation is present if you use Spring Security
//    public ResponseEntity<PaymentIntentResponse> createPaymentIntent(
//            @RequestBody PaymentIntentCreateRequest request,
//            @AuthenticationPrincipal User authenticatedUser
//    ) {
//        logger.debug("PaymentController Debug: createPaymentIntent called.");
//        if (authenticatedUser == null) {
//            logger.warn("PaymentController: Unauthenticated user attempt to create payment intent.");
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null); // Return null body for type safety
//        }
//
//        logger.debug("PaymentController Debug: Authenticated User: {}", authenticatedUser.getEmail());
//        logger.debug("PaymentController Debug: User Roles: {}", authenticatedUser.getAuthorities());
//        logger.debug("PaymentController Debug: Received request payload: {}", request);
//
//        // Basic validation for input before service call
//        if (request.getRentRequestId() == null || request.getAmount() == null ||
//                request.getCurrency() == null || request.getPropertyId() == null) {
//            logger.warn("PaymentController: Missing required fields in payment intent creation request. Payload: {}", request);
//            return ResponseEntity.badRequest().body(new PaymentIntentResponse(null, null, "Missing required payment details."));
//        }
//
//        try {
//            PaymentIntent paymentIntent = paymentService.createPaymentIntent(
//                    request.getAmount(),
//                    request.getCurrency(),
//                    "Rent payment for property ID: " + request.getPropertyId(), // Description for Stripe
//                    request.getRentRequestId(),
//                    request.getPropertyId(),
//                    authenticatedUser
//            );
//
//            logger.debug("PaymentController Debug: Stripe PaymentIntent created/retrieved. ID: {}, Status: {}", paymentIntent.getId(), paymentIntent.getStatus());
//            logger.debug("PaymentController Debug: Client Secret (from Stripe): {}", paymentIntent.getClientSecret() != null ? "Present" : "Missing");
//
//            PaymentIntentResponse response = new PaymentIntentResponse(
//                    paymentIntent.getClientSecret(),
//                    paymentService.getStripePublishableKey(),
//                    paymentIntent.getStatus()
//            );
//
//            return ResponseEntity.ok(response);
//
//        } catch (StripeException e) {
//            logger.error("PaymentController: Stripe API Error during createPaymentIntent: {}", e.getMessage(), e);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(new PaymentIntentResponse(null, null, "Failed to process payment with Stripe. Please try again or contact support."));
//        } catch (RuntimeException e) {
//            logger.error("PaymentController: Application Logic Error during createPaymentIntent: {}", e.getMessage(), e);
//            // This catches your custom exceptions (e.g., rent request not approved, payment already completed)
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                    .body(new PaymentIntentResponse(null, null, e.getMessage())); // Return error message to frontend
//        } catch (Exception e) {
//            logger.error("PaymentController: Unexpected error during createPaymentIntent: {}", e.getMessage(), e);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(new PaymentIntentResponse(null, null, "An unexpected error occurred. Please try again."));
//        }
//    }
//
//    /**
//     * Endpoint to confirm a payment status from the frontend.
//     * It requires a RENTER role.
//     *
//     * @param request Contains paymentIntentId, status (e.g., "succeeded"), and rentRequestId.
//     * @param authenticatedUser The authenticated user (renter).
//     * @return 200 OK if successful.
//     */
//    @PostMapping("/confirm-payment")
//    @PreAuthorize("hasRole('RENTER')") // Ensure this annotation is present if you use Spring Security
//    public ResponseEntity<String> confirmPayment(
//            @RequestBody PaymentIntentConfirmRequest request, // NOW USES THE NEW DTO
//            @AuthenticationPrincipal User authenticatedUser
//    ) {
//        logger.debug("PaymentController Debug: confirmPayment called.");
//        if (authenticatedUser == null) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated.");
//        }
//        logger.debug("PaymentController Debug: Authenticated User for confirmPayment: {}", authenticatedUser.getEmail());
//        logger.debug("PaymentController Debug: Received confirmation payload: {}", request);
//
//        // Basic validation for input
//        if (request.getPaymentIntentId() == null || request.getStatus() == null || request.getRentRequestId() == null) {
//            logger.warn("PaymentController: Missing required fields for payment confirmation.");
//            return ResponseEntity.badRequest().body("Missing required confirmation details (paymentIntentId, status, rentRequestId).");
//        }
//
//        try {
//            paymentService.updatePaymentStatus(
//                    request.getPaymentIntentId(),
//                    request.getStatus(),
//                    request.getRentRequestId(),
//                    authenticatedUser
//            );
//            logger.info("PaymentController: Payment intent {} status updated to {}", request.getPaymentIntentId(), request.getStatus());
//            return ResponseEntity.ok("Payment status updated successfully.");
//        } catch (RuntimeException e) {
//            logger.error("PaymentController: Application Logic Error during confirmPayment: {}", e.getMessage(), e);
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                    .body(e.getMessage());
//        } catch (Exception e) {
//            logger.error("PaymentController: Unexpected error during confirmPayment: {}", e.getMessage(), e);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("An unexpected error occurred during payment confirmation.");
//        }
//    }
//} jun 13 12:35am

//@RestController
//@RequestMapping("/api/payments")
//@RequiredArgsConstructor
//public class PaymentController {
//
//    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);
//
//    private final PaymentService paymentService;
//
//    @PostMapping("/create-payment-intent")
//    @PreAuthorize("hasRole('RENTER')")
//    public ResponseEntity<PaymentIntentResponse> createPaymentIntent(
//            @Valid @RequestBody PaymentIntentCreateRequest request,
//            @AuthenticationPrincipal User authenticatedUser
//    ) {
//        logger.debug("PaymentController Debug: createPaymentIntent called.");
//
//        if (authenticatedUser == null) {
//            logger.warn("PaymentController: Unauthenticated user attempt to create payment intent (authenticatedUser is null).");
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new PaymentIntentResponse(null, null, "Authentication required."));
//        }
//
//        logger.debug("PaymentController Debug: Authenticated User: {}", authenticatedUser.getEmail());
//        logger.debug("PaymentController Debug: User Roles: {}", authenticatedUser.getAuthorities());
//        logger.debug("PaymentController Debug: Received request payload: {}", request);
//
//        if (request.getRentRequestId() == null || request.getAmount() == null ||
//                request.getCurrency() == null || request.getPropertyId() == null) {
//            logger.warn("PaymentController: Missing required fields in payment intent creation request. Payload: {}", request);
//            return ResponseEntity.badRequest().body(new PaymentIntentResponse(null, null, "Missing required payment details."));
//        }
//
//        try {
//            PaymentIntent paymentIntent = paymentService.createPaymentIntent(
//                    request.getAmount(),
//                    request.getCurrency(),
//                    "Rent payment for property ID: " + request.getPropertyId(),
//                    request.getRentRequestId(),
//                    request.getPropertyId(),
//                    authenticatedUser
//            );
//
//            logger.debug("PaymentController Debug: Stripe PaymentIntent created/retrieved. ID: {}, Status: {}", paymentIntent.getId(), paymentIntent.getStatus());
//            logger.debug("PaymentController Debug: Client Secret (from Stripe): {}", paymentIntent.getClientSecret() != null ? "Present" : "Missing");
//
//            PaymentIntentResponse response = new PaymentIntentResponse(
//                    paymentIntent.getClientSecret(),
//                    paymentService.getStripePublishableKey(),
//                    paymentIntent.getStatus()
//            );
//
//            return ResponseEntity.ok(response);
//
//        } catch (StripeException e) {
//            logger.error("PaymentController: Stripe API Error during createPaymentIntent: {}", e.getMessage(), e);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(new PaymentIntentResponse(null, null, "Failed to process payment with Stripe. Please try again or contact support."));
//        } catch (RuntimeException e) {
//            logger.error("PaymentController: Application Logic Error during createPaymentIntent: {}", e.getMessage(), e);
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                    .body(new PaymentIntentResponse(null, null, e.getMessage()));
//        } catch (Exception e) {
//            logger.error("PaymentController: Unexpected error during createPaymentIntent: {}", e.getMessage(), e);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(new PaymentIntentResponse(null, null, "An unexpected error occurred. Please try again."));
//        }
//    }
//
//    @PostMapping("/confirm-payment")
//    @PreAuthorize("hasRole('RENTER')")
//    public ResponseEntity<String> confirmPayment(
//            @Valid @RequestBody PaymentIntentConfirmRequest request,
//            @AuthenticationPrincipal User authenticatedUser
//    ) {
//        logger.debug("PaymentController Debug: confirmPayment called.");
//        if (authenticatedUser == null) {
//            logger.warn("PaymentController: Unauthenticated user attempt to confirm payment (authenticatedUser is null).");
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated.");
//        }
//        logger.debug("PaymentController Debug: Authenticated User for confirmPayment: {}", authenticatedUser.getEmail());
//        logger.debug("PaymentController Debug: Received confirmation payload: {}", request);
//
//        if (request.getPaymentIntentId() == null || request.getStatus() == null || request.getRentRequestId() == null) {
//            logger.warn("PaymentController: Missing required fields for payment confirmation.");
//            return ResponseEntity.badRequest().body("Missing required confirmation details (paymentIntentId, status, rentRequestId).");
//        }
//
//        try {
//            paymentService.updatePaymentStatus(
//                    request.getPaymentIntentId(),
//                    request.getStatus(),
//                    request.getRentRequestId(),
//                    authenticatedUser
//            );
//            logger.info("PaymentController: Payment intent {} status updated to {}", request.getPaymentIntentId(), request.getStatus());
//            return ResponseEntity.ok("Payment status updated successfully.");
//        } catch (RuntimeException e) {
//            logger.error("PaymentController: Application Logic Error during confirmPayment: {}", e.getMessage(), e);
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                    .body(e.getMessage());
//        } catch (Exception e) {
//            logger.error("PaymentController: Unexpected error during confirmPayment: {}", e.getMessage(), e);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("An unexpected error occurred during payment confirmation.");
//        }
//    }
//}
//
// jun 23 final code
@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);

    private final PaymentService paymentService;
    private final RentRequestService rentRequestService; // Needed to fetch RentRequest details for validation/amount
    private final UserService userService; // Needed to verify authenticated user

    @Value("${stripe.api.secret-key}")
    private String stripeSecretKey;


    @PostMapping("/create-payment-intent")
    @PreAuthorize("hasRole('RENTER')")
    public ResponseEntity<PaymentIntentResponse> createPaymentIntent(
            @Valid @RequestBody PaymentIntentCreateRequest request,
            @AuthenticationPrincipal User authenticatedUser
    ) {
        Stripe.apiKey = stripeSecretKey; // Set Stripe API key for the request
        logger.debug("PaymentController Debug: createPaymentIntent called.");

        if (authenticatedUser == null) {
            logger.warn("PaymentController: Unauthenticated user attempt to create payment intent (authenticatedUser is null).");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new PaymentIntentResponse(null, null, "Authentication required."));
        }

        logger.debug("PaymentController Debug: Authenticated User: {}", authenticatedUser.getEmail());
        logger.debug("PaymentController Debug: User Roles: {}", authenticatedUser.getAuthorities());
        logger.debug("PaymentController Debug: Received request payload: {}", request);

        // Basic null checks, @Valid handles most others
        if (request.getRentRequestId() == null || request.getAmount() == null ||
                request.getCurrency() == null || request.getPropertyId() == null) {
            logger.warn("PaymentController: Missing required fields in payment intent creation request. Payload: {}", request);
            return ResponseEntity.badRequest().body(new PaymentIntentResponse(null, null, "Missing required payment details."));
        }

        try {
            // Delegate to PaymentService to handle the creation/retrieval of Stripe PaymentIntent
            // and saving the payment record in the local database.
            PaymentIntent paymentIntent = paymentService.createPaymentIntent(
                    request.getAmount(),
                    request.getCurrency(),
                    request.getDescription(), // Pass description from request
                    request.getRentRequestId(),
                    request.getPropertyId(),
                    authenticatedUser
            );

            logger.debug("PaymentController Debug: Stripe PaymentIntent created/retrieved. ID: {}, Status: {}", paymentIntent.getId(), paymentIntent.getStatus());
            logger.debug("PaymentController Debug: Client Secret (from Stripe): {}", paymentIntent.getClientSecret() != null ? "Present" : "Missing");

            // Build response for frontend, including publishable key from PaymentService
            PaymentIntentResponse response = new PaymentIntentResponse(
                    paymentIntent.getClientSecret(),
                    paymentService.getStripePublishableKey(), // Get publishable key from PaymentService
                    paymentIntent.getStatus()
            );

            return ResponseEntity.ok(response);

        } catch (StripeException e) {
            logger.error("PaymentController: Stripe API Error during createPaymentIntent: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new PaymentIntentResponse(null, null, "Failed to process payment with Stripe. Please try again or contact support."));
        } catch (RuntimeException e) { // Catch custom application-specific exceptions
            logger.error("PaymentController: Application Logic Error during createPaymentIntent: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new PaymentIntentResponse(null, null, e.getMessage()));
        } catch (Exception e) { // Catch any other unexpected exceptions
            logger.error("PaymentController: Unexpected error during createPaymentIntent: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new PaymentIntentResponse(null, null, "An unexpected error occurred. Please try again."));
        }
    }


    @PostMapping("/confirm-payment")
    @PreAuthorize("hasRole('RENTER')") // Ensure user is authenticated as RENTER to call this
    public ResponseEntity<Map<String, Object>> confirmPayment( // Corrected return type
                                                               @Valid @RequestBody PaymentIntentConfirmRequest request,
                                                               @AuthenticationPrincipal User authenticatedUser
    ) {
        logger.debug("PaymentController Debug: confirmPayment called.");
        if (authenticatedUser == null) {
            logger.warn("PaymentController: Unauthenticated user attempt to confirm payment (authenticatedUser is null).");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("success", false, "message", "User not authenticated."));
        }
        logger.debug("PaymentController Debug: Authenticated User for confirmPayment: {}", authenticatedUser.getEmail());
        logger.debug("PaymentController Debug: Received confirmation payload: {}", request);

        // Basic null checks for the request body
        if (request.getPaymentIntentId() == null || request.getStatus() == null || request.getRentRequestId() == null) {
            logger.warn("PaymentController: Missing required fields for payment confirmation.");
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", "Missing required confirmation details (paymentIntentId, status, rentRequestId)."));
        }

        try {
            // Delegate to PaymentService to update status in DB
            paymentService.updatePaymentStatus(
                    request.getPaymentIntentId(),
                    request.getStatus(),
                    request.getRentRequestId(),
                    authenticatedUser
            );
            logger.info("PaymentController: Payment intent {} status updated to {}", request.getPaymentIntentId(), request.getStatus());
            // Return a JSON object with 'success: true' for frontend
            return ResponseEntity.ok(Map.of("success", true, "message", "Payment status updated successfully."));
        } catch (RuntimeException e) {
            logger.error("PaymentController: Application Logic Error during confirmPayment: {}", e.getMessage(), e);
            // Return a JSON object with 'success: false' and the error message
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("success", false, "message", e.getMessage()));
        } catch (Exception e) {
            logger.error("PaymentController: Unexpected error during confirmPayment: {}", e.getMessage(), e);
            // Return a generic error object with 'success: false'
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("success", false, "message", "An unexpected error occurred during payment confirmation."));
        }
    }
}
