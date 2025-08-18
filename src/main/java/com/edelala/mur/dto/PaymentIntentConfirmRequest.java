package com.edelala.mur.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentIntentConfirmRequest {

    @NotBlank(message = "Payment Intent ID is required")
    private String paymentIntentId;

    @NotBlank(message = "Status is required")
    private String status; // e.g., "succeeded", "failed"

    @NotNull(message = "Rent Request ID is required for confirmation")
    private Long rentRequestId;
}
