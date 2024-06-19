package com.lalabrand.ecommerce.payment.card;

import com.lalabrand.ecommerce.order.OrderService;
import com.lalabrand.ecommerce.order.enums.Currency;
import com.lalabrand.ecommerce.order.shipping.shipping_info.ShippingInfoRequest;
import com.lalabrand.ecommerce.utils.CommonUtils;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;

@Controller
public class CardPaymentController {

    private final OrderService orderService;

    @Value("${stripe.api.key}")
    private String StripeApiKey;

    @Autowired
    public CardPaymentController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostConstruct
    public void setup() {
        Stripe.apiKey = StripeApiKey;
    }

    @MutationMapping(name = "createPaymentCard")
    public CardPaymentResponse createPaymentCard(@Argument("currency") Currency currency,
                                                 @Argument("shippingInfo") ShippingInfoRequest shippingInfoRequest) throws StripeException {
        BigDecimal totalCost = orderService.calculateTotal(CommonUtils.getCurrentUserId());
        PaymentIntentCreateParams params =
                PaymentIntentCreateParams.builder()
                        .setAmount(Long.valueOf(String.valueOf(totalCost.multiply(BigDecimal.valueOf(100)))))
                        .setCurrency(currency.toString().toLowerCase())
                        .setAutomaticPaymentMethods(
                                PaymentIntentCreateParams.AutomaticPaymentMethods
                                        .builder()
                                        .setEnabled(true)
                                        .build()
                        )
                        .build();

        PaymentIntent paymentIntent = PaymentIntent.create(params);

        orderService.placeOrder(CommonUtils.getCurrentUserId(), shippingInfoRequest, currency);

        return CardPaymentResponse.builder()
                .success(true)
                .message("Success to create payment")
                .clientSecret(paymentIntent.getClientSecret()).
                build();
    }
}
