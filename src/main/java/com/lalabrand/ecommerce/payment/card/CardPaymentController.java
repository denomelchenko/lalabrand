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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

@Controller
public class CardPaymentController {

    private static final Logger logger = LoggerFactory.getLogger(CardPaymentController.class);

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
        String userId = CommonUtils.getCurrentUserId();
        logger.info("User {} is attempting to create a payment card with currency {} and shipping info {}", userId, currency, shippingInfoRequest);

        Float totalCost = orderService.calculateTotal(userId);
        logger.info("Calculated total cost for user {}: {}", userId, totalCost);

        Float discount = orderService.calculateDiscount(userId);
        logger.info("Calculated discount for user {}: {}", userId, totalCost);

        PaymentIntentCreateParams params =
                PaymentIntentCreateParams.builder()
                        .setAmount(Long.valueOf(String.valueOf(totalCost - (discount * 100))))
                        .setCurrency(currency.toString().toLowerCase())
                        .setAutomaticPaymentMethods(
                                PaymentIntentCreateParams.AutomaticPaymentMethods
                                        .builder()
                                        .setEnabled(true)
                                        .build()
                        )
                        .build();

        logger.debug("PaymentIntentCreateParams: {}", params);

        PaymentIntent paymentIntent = PaymentIntent.create(params);
        logger.info("Created PaymentIntent for user {}: {}", userId, paymentIntent.getId());

        orderService.placeOrder(userId, shippingInfoRequest, currency);
        logger.info("Placed order for user {}", userId);

        return CardPaymentResponse.builder()
                .success(true)
                .message("Success to create payment")
                .clientSecret(paymentIntent.getClientSecret())
                .build();
    }
}
