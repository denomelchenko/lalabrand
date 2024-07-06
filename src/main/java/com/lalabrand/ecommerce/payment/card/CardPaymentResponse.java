package com.lalabrand.ecommerce.payment.card;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CardPaymentResponse {
    private boolean success;
    private String message;
    private String clientSecret;
}
