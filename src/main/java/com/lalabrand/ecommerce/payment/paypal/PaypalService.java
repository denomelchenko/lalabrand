package com.lalabrand.ecommerce.payment.paypal;

import com.paypal.api.payments.Payment;
import org.springframework.stereotype.Service;

@Service
public interface PaypalService {
    Payment createPayment(String userId, String currency, String method, String successUrl, String cancelUrl);
    Payment executePayment(String paymentId, String payerId);
}
