package com.edelala.mur.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

//@Data // Lombok annotation for getters, setters, equals, hashCode, and toString
//@NoArgsConstructor // Lombok annotation for a no-argument constructor
//@AllArgsConstructor // Lombok annotation for a constructor with all fields
//public class PaymentIntentCreateRequest {
//
//    @NotNull(message = "Amount is required")
//    @DecimalMin(value = "0.50", message = "Amount must be at least 0.50 (USD equivalent)")
//    private BigDecimal amount;
//
//    @NotBlank(message = "Currency is required")
//    @Pattern(regexp = "^[A-Z]{3}$", message = "Currency must be a 3-letter ISO code (e.g., USD)")
//    private String currency; // e.g., "usd", "eur"
//
//    @NotNull(message = "Property ID is required")
//    private Long propertyId;
//
//    @NotNull(message = "Rent Request ID is required")
//    private Long rentRequestId;
//}
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//public class PaymentIntentCreateRequest {
//
//    @NotNull(message = "Amount is required")
//    @DecimalMin(value = "0.50", message = "Amount must be at least 0.50 (USD equivalent)")
//    private BigDecimal amount;
//
//    @NotBlank(message = "Currency is required")
//    @Pattern(regexp = "^[A-Z]{3}$", message = "Currency must be a 3-letter ISO code (e.g., USD)")
//    private String currency;
//
//    @NotNull(message = "Property ID is required")
//    private Long propertyId;
//
//    @NotNull(message = "Rent Request ID is required")
//    private Long rentRequestId;
//} jun 18

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentIntentCreateRequest {

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.50", message = "Amount must be at least 0.50 (USD equivalent)")
    private BigDecimal amount;

    @NotBlank(message = "Currency is required")
    @Pattern(regexp = "^[A-Z]{3}$", message = "Currency must be a 3-letter ISO code (e.g., USD)")
    private String currency;

    @NotNull(message = "Property ID is required")
    private Long propertyId;

    @NotNull(message = "Rent Request ID is required")
    private Long rentRequestId;

    private String description; // Added this field to align with PaymentService method signature
}

