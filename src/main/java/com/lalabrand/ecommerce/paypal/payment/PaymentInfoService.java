package com.lalabrand.ecommerce.paypal.payment;

import com.lalabrand.ecommerce.order.enums.Currency;
import com.lalabrand.ecommerce.order.shipping.shipping_info.ShippingInfoRequest;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class PaymentInfoService {
    private Map<String, PaymentInfo> paymentInfoMap = new ConcurrentHashMap<>();

    public void savePaymentInfo(String paymentId, Currency currency, ShippingInfoRequest shippingInfoRequest) {
        PaymentInfo paymentInfo = new PaymentInfo();
        paymentInfo.setPaymentId(paymentId);
        paymentInfo.setCurrency(currency);
        paymentInfo.setShippingInfoRequest(shippingInfoRequest);
        paymentInfoMap.put(paymentId, paymentInfo);
    }

    public PaymentInfo getPaymentInfo(String paymentId) {
        return paymentInfoMap.get(paymentId);
    }
}

