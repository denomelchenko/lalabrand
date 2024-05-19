package com.lalabrand.ecommerce.paypal.payment;

import com.lalabrand.ecommerce.order.enums.Currency;
import com.lalabrand.ecommerce.order.shipping.shipping_info.ShippingInfoRequest;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PaymentInfo {
    private String paymentId;
    private String payerId;
    private Currency currency;
    private ShippingInfoRequest shippingInfoRequest;

}
