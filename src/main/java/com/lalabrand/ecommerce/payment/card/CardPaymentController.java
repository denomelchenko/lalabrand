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
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.util.Locale;

@Controller
public class CardPaymentController {

    private final CommonUtils commonUtils;
    private final OrderService orderService;

    @Autowired
    public CardPaymentController(OrderService orderService, CommonUtils commonUtils) {
        this.orderService = orderService;
        this.commonUtils = commonUtils;
    }

    @PostConstruct
    public void setup() {
        Stripe.apiKey = "sk_test_51PT0Q8P1691tICenOhQEIoDc0fUFR0XOG7J2VbpYjALSu1fNhcUfYlKZq9Gqklv4uCDuVNPd0XUzIEmcAl367I1p00O0C2JB2k";
    }

    @MutationMapping(name = "createPaymentCard") // /create-payment-intent
    public CardPaymentResponse createPaymentCard(@Argument("currency") Currency currency,
                                                 @Argument("shippingInfo") ShippingInfoRequest shippingInfoRequest) throws StripeException {
        BigDecimal totalCost = orderService.calculateTotal(commonUtils.getCurrentUser().getId());
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

        orderService.placeOrder(commonUtils.getCurrentUser().getId(), shippingInfoRequest, currency);

        return CardPaymentResponse.builder()
                .success(true)
                .message("Success to create payment")
                .clientSecret(paymentIntent.getClientSecret()).
                build();
    }
}
