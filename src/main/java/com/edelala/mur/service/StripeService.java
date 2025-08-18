package com.edelala.mur.service;

import com.edelala.mur.entity.Payment;
import com.edelala.mur.entity.Property;
import com.edelala.mur.entity.RentRequest;
import com.edelala.mur.repo.PaymentRepository;
import com.edelala.mur.repo.PropertyRepository;
import com.edelala.mur.repo.RentRequestRepository;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct; // For @PostConstruct

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;


//@Service
//public class StripeService {
//
//    @Value("${stripe.api.secret-key}")
//    private String secretKey;
//
//    @Value("${stripe.api.publishable-key}")
//    private String publishableKey;
//
//    // FIX: Updated @Value annotations to match property names in application.properties
//    @Value("${jwt.secret}") // Inject the JWT secret here temporarily for debugging
//    private String jwtSecretForDebug; // A new field to hold the JWT secret for printing
//
//
//    @PostConstruct
//    public void init() {
//        Stripe.apiKey = secretKey;
//        // TEMPORARY DEBUG: Print the loaded JWT secret to console
//        System.out.println("DEBUG: Loaded JWT Secret: " + jwtSecretForDebug);
//    }
//
//    public PaymentIntent createPaymentIntent(Long amount, String currency, String description) throws StripeException {
//        PaymentIntentCreateParams params =
//                PaymentIntentCreateParams.builder()
//                        .setAmount(amount) // Amount in smallest currency unit (e.g., cents)
//                        .setCurrency(currency)
//                        .setDescription(description)
//                        .setAutomaticPaymentMethods(
//                                PaymentIntentCreateParams.AutomaticPaymentMethods.builder()
//                                        .setEnabled(true)
//                                        .build()
//                        )
//                        .build();
//        System.out.println("Creating Stripe PaymentIntent for amount: " + amount + " " + currency);
//        return PaymentIntent.create(params);
//    }
//
//    public String getPublishableKey() {
//        return publishableKey;
//    }
//} jun 13

//@Service
//public class StripeService {
//
//    @Value("${stripe.api.secret-key}")
//    private String secretKey;
//
//    @Value("${stripe.api.publishable-key}")
//    private String publishableKey;
//
//    @Value("${jwt.secret}") // For debug logging, can be removed later
//    private String jwtSecretForDebug;
//
//    @PostConstruct
//    public void init() {
//        Stripe.apiKey = secretKey;
//        System.out.println("StripeService Debug: Initialized Stripe API with secret key.");
//    }
//
//    public PaymentIntent createPaymentIntent(Long amount, String currency, String description) throws StripeException {
//        PaymentIntentCreateParams params =
//                PaymentIntentCreateParams.builder()
//                        .setAmount(amount)
//                        .setCurrency(currency)
//                        .setDescription(description)
//                        .setAutomaticPaymentMethods(
//                                PaymentIntentCreateParams.AutomaticPaymentMethods.builder()
//                                        .setEnabled(true)
//                                        .build()
//                        )
//                        .build();
//        System.out.println("StripeService: Creating Stripe PaymentIntent for amount: " + amount + " " + currency);
//        return PaymentIntent.create(params);
//    }
//
//    public PaymentIntent retrievePaymentIntent(String paymentIntentId) throws StripeException {
//        System.out.println("StripeService: Retrieving PaymentIntent with ID: " + paymentIntentId);
//        return PaymentIntent.retrieve(paymentIntentId);
//    }
//
//    public String getPublishableKey() {
//        return publishableKey;
//    }
//} jun 13 12:32 am

//@Service
//public class StripeService {
//
//    @Value("${stripe.api.secret-key}")
//    private String secretKey;
//
//    @Value("${stripe.api.publishable-key}")
//    private String publishableKey;
//
//    @Value("${jwt.secret}")
//    private String jwtSecretForDebug;
//
//    @PostConstruct
//    public void init() {
//        Stripe.apiKey = secretKey;
//        System.out.println("StripeService Debug: Initialized Stripe API with secret key.");
//    }
//
//    public PaymentIntent createPaymentIntent(Long amount, String currency, String description) throws StripeException {
//        PaymentIntentCreateParams params =
//                PaymentIntentCreateParams.builder()
//                        .setAmount(amount)
//                        .setCurrency(currency)
//                        .setDescription(description)
//                        .setAutomaticPaymentMethods(
//                                PaymentIntentCreateParams.AutomaticPaymentMethods.builder()
//                                        .setEnabled(true)
//                                        .build()
//                        )
//                        .build();
//        System.out.println("StripeService: Creating Stripe PaymentIntent for amount: " + amount + " " + currency);
//        return PaymentIntent.create(params);
//    }
//
//    public PaymentIntent retrievePaymentIntent(String paymentIntentId) throws StripeException {
//        System.out.println("StripeService: Retrieving PaymentIntent with ID: " + paymentIntentId);
//        return PaymentIntent.retrieve(paymentIntentId);
//    }
//
//    public String getPublishableKey() {
//        return publishableKey;
//    }
//}
// jun 14 12:39

//@Service
//public class StripeService {
//
//    @Value("${stripe.api.secret-key}")
//    private String secretKey;
//
//    @Value("${stripe.api.publishable-key}") // <<< THIS LINE IS CRITICAL >>>
//    private String publishableKey;
//
//    @Value("${jwt.secret}")
//    private String jwtSecretForDebug;
//
//    @PostConstruct
//    public void init() {
//        Stripe.apiKey = secretKey;
//        System.out.println("StripeService Debug: Initialized Stripe API with secret key.");
//    }
//
//    public PaymentIntent createPaymentIntent(Long amount, String currency, String description) throws StripeException {
//        PaymentIntentCreateParams params =
//                PaymentIntentCreateParams.builder()
//                        .setAmount(amount)
//                        .setCurrency(currency)
//                        .setDescription(description)
//                        .setAutomaticPaymentMethods(
//                                PaymentIntentCreateParams.AutomaticPaymentMethods.builder()
//                                        .setEnabled(true)
//                                        .build()
//                        )
//                        .build();
//        System.out.println("StripeService: Creating Stripe PaymentIntent for amount: " + amount + " " + currency);
//        return PaymentIntent.create(params);
//    }
//
//    public PaymentIntent retrievePaymentIntent(String paymentIntentId) throws StripeException {
//        System.out.println("StripeService: Retrieving PaymentIntent with ID: " + paymentIntentId);
//        return PaymentIntent.retrieve(paymentIntentId);
//    }
//
//    public String getPublishableKey() {
//        return publishableKey;
//    }
//}
//jun 14 7:56 pm working code

@Service
public class StripeService {

    @Value("${stripe.api.secret-key}")
    private String secretKey;

    @Value("${stripe.api.publishable-key}") // <<< THIS LINE IS CRITICAL >>>
    private String publishableKey;

    @Value("${jwt.secret}")
    private String jwtSecretForDebug;

    @PostConstruct
    public void init() {
        Stripe.apiKey = secretKey;
        System.out.println("StripeService Debug: Initialized Stripe API with secret key.");
    }

    public PaymentIntent createPaymentIntent(Long amount, String currency, String description) throws StripeException {
        PaymentIntentCreateParams params =
                PaymentIntentCreateParams.builder()
                        .setAmount(amount)
                        .setCurrency(currency)
                        .setDescription(description)
                        .setAutomaticPaymentMethods(
                                PaymentIntentCreateParams.AutomaticPaymentMethods.builder()
                                        .setEnabled(true)
                                        .build()
                        )
                        .build();
        System.out.println("StripeService: Creating Stripe PaymentIntent for amount: " + amount + " " + currency);
        return PaymentIntent.create(params);
    }

    public PaymentIntent retrievePaymentIntent(String paymentIntentId) throws StripeException {
        System.out.println("StripeService: Retrieving PaymentIntent with ID: " + paymentIntentId);
        return PaymentIntent.retrieve(paymentIntentId);
    }

    public String getPublishableKey() {
        return publishableKey;
    }
}
