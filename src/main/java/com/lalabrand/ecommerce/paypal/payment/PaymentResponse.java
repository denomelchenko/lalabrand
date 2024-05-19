package com.lalabrand.ecommerce.paypal.payment;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentResponse {
    private boolean success;
    private String message;
    private String redirectUrl;
    private HttpStatus status;
}

