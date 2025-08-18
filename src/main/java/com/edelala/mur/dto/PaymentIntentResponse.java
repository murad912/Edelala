package com.edelala.mur.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentIntentResponse {
    private String clientSecret;
    private String publishableKey; // Include this for frontend convenience
    private String status; // Also useful for frontend
}

