package com.lalabrand.ecommerce.payment.paypal;

import com.lalabrand.ecommerce.order.OrderService;
import com.lalabrand.ecommerce.order.enums.Currency;
import com.lalabrand.ecommerce.order.shipping.shipping_info.ShippingInfoRequest;
import com.lalabrand.ecommerce.payment.paypal.payment.PaymentInfo;
import com.lalabrand.ecommerce.payment.paypal.payment.PaymentInfoService;
import com.lalabrand.ecommerce.payment.paypal.payment.PaymentResponse;
import com.lalabrand.ecommerce.utils.CommonResponse;
import com.lalabrand.ecommerce.utils.CommonUtils;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;

@Controller
public class PaypalController {

    private static final Logger logger = LoggerFactory.getLogger(PaypalController.class);

    @Value("${payment.success.url}")
    private String successUrl;
    @Value("${payment.cancel.url}")
    private String cancelUrl;
    @Value("${payment.error.url}")
    private String errorUrl;
    private final PaypalService paypalService;
    private final CommonUtils commonUtils;
    private final OrderService orderService;
    private final PaymentInfoService paymentInfoService;



    @Autowired
    public PaypalController(PaypalService paypalService, CommonUtils commonUtils, OrderService orderService, PaymentInfoService paymentInfoService) {
        this.paypalService = paypalService;
        this.commonUtils = commonUtils;
        this.orderService = orderService;
        this.paymentInfoService = paymentInfoService;
    }

    @MutationMapping(name = "createPayment")
    public PaymentResponse createPayment(@Argument("currency") Currency currency,
                                         @Argument("shippingInfo") ShippingInfoRequest shippingInfoRequest) {
        logger.info("Creating PayPal payment for user {}", commonUtils.getCurrentUser().getId());

        Payment payment = paypalService.createPayment(commonUtils.getCurrentUser().getId(), String.valueOf(currency), "paypal", successUrl, cancelUrl);
        for (Links links : payment.getLinks()) {
            if (links.getRel().equals("approval_url")) {
                logger.info("Successfully created PayPal payment for user {}", commonUtils.getCurrentUser().getId());

                paymentInfoService.savePaymentInfo(payment.getId(), currency, shippingInfoRequest);

                return new PaymentResponse(true, "Success to create payment", links.getHref(), HttpStatus.OK);
            }
        }
        logger.error("Failed to create PayPal payment for user {}", commonUtils.getCurrentUser().getId());
        return new PaymentResponse(false, "Failed to create payment", errorUrl, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @QueryMapping("paymentSuccess")
    public PaymentResponse paymentSuccess(@Argument("paymentId") String paymentId,
                                          @Argument("payerID") String payerId) {
        Payment payment = paypalService.executePayment(paymentId, payerId);
        if (payment.getState().equals("approved")) {
            logger.info("Payment approved for paymentId {}", paymentId);

            PaymentInfo paymentInfo = paymentInfoService.getPaymentInfo(paymentId);
            Currency currency = paymentInfo.getCurrency();
            ShippingInfoRequest shippingInfoRequest = paymentInfo.getShippingInfoRequest();

            CommonResponse orderResponse = orderService.placeOrder(commonUtils.getCurrentUser().getId(), shippingInfoRequest, currency);
            if (orderResponse.isSuccess()) {
                logger.info("Order placed successfully for user {}", commonUtils.getCurrentUser().getId());
                return new PaymentResponse(true, "Success to execute payment and place order", "http://localhost:9091/home", HttpStatus.OK);
            } else {
                logger.error("Order placement failed for user {}", commonUtils.getCurrentUser().getId());
                return new PaymentResponse(true, "Payment executed but failed to place order", "http://localhost:9091/home", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        logger.error("Payment execution failed for paymentId {}", paymentId);
        return new PaymentResponse(false, "Failed to execute payment", cancelUrl, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @QueryMapping("paymentCancel")
    public CommonResponse paymentCancel() {
        logger.info("Payment was cancelled");
        return CommonResponse.builder()
                .message("Payment was cancelled executed")
                .success(false)
                .build();
    }

    @QueryMapping("paymentError")
    public CommonResponse paymentError() {
        logger.info("Error with payment");
        return CommonResponse.builder()
                .message("Error with payment")
                .success(false)
                .build();
    }
}
