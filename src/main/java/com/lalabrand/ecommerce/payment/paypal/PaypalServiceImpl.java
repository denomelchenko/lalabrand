package com.lalabrand.ecommerce.payment.paypal;

import com.lalabrand.ecommerce.exception.PayPalException;
import com.lalabrand.ecommerce.order.OrderService;
import com.lalabrand.ecommerce.user.User;
import com.lalabrand.ecommerce.user.UserService;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class PaypalServiceImpl implements PaypalService {

    private static final Logger logger = LoggerFactory.getLogger(PaypalServiceImpl.class);

    private final OrderService orderService;
    private final UserService userService;
    private APIContext apiContext;

    @Autowired
    public PaypalServiceImpl(OrderService orderService, UserService userService, APIContext apiContext) {
        this.orderService = orderService;
        this.userService = userService;
        this.apiContext = apiContext;
    }

    @Override
    public Payment createPayment(
            String userId,
            String currency,
            String method,
            String successUrl,
            String cancelUrl
    ) {
        logger.info("Creating payment for userId: {}, currency: {}, method: {}, successUrl: {}, cancelUrl: {}",
                userId, currency, method, successUrl, cancelUrl);
        Payment payment = new Payment();
        payment.setIntent("SALE");
        payment.setPayer(getPayer(userId, method));

        Float discount = orderService.calculateDiscount(userId);
        logger.debug("Calculated discount for userId {}: {}", userId, discount);
        Float totalCost = orderService.calculateTotal(userId);
        logger.debug("Calculated total cost for userId {}: {}", userId, totalCost);

        payment.setTransactions(getTransactions(currency, totalCost - discount));
        payment.setRedirectUrls(getRedirectUrls(successUrl, cancelUrl));

        try {
            Payment createdPayment = payment.create(apiContext);
            logger.info("Payment created successfully: {}", createdPayment);
            return createdPayment;
        } catch (PayPalRESTException e) {
            logger.error("Failed to create PayPal payment", e);
            throw new PayPalException("Failed to create PayPal payment", e);
        }
    }

    private Payer getPayer(String userId, String method) {
        logger.info("Getting payer info for userId: {}", userId);
        User user = userService.findByUserId(userId).orElseThrow(EntityNotFoundException::new);
        PayerInfo payerInfo = new PayerInfo();
        payerInfo.setPayerId(userId);
        payerInfo.setEmail(user.getEmail());
        payerInfo.setFirstName(user.getFirstName());
        payerInfo.setLastName(user.getLastName());

        Payer payer = new Payer();
        payer.setPayerInfo(payerInfo);
        payer.setPaymentMethod(method);
        return payer;
    }

    private List<Transaction> getTransactions(String currency, Float total) {
        logger.info("Creating transaction with currency: {} and total: {}", currency, total);
        Amount amount = new Amount();
        amount.setCurrency(String.valueOf(currency));
        amount.setTotal(String.format(Locale.forLanguageTag(String.valueOf(currency)), String.valueOf(total)));

        Transaction transaction = new Transaction();
        transaction.setDescription("description of purchase");
        transaction.setAmount(amount);

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);
        return transactions;
    }

    private RedirectUrls getRedirectUrls(String successUrl, String cancelUrl) {
        logger.info("Setting redirect URLs: successUrl: {}, cancelUrl: {}", successUrl, cancelUrl);
        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setReturnUrl(successUrl);
        redirectUrls.setCancelUrl(cancelUrl);
        return redirectUrls;
    }

    @Override
    public Payment executePayment(
            String paymentId,
            String payerId
    ) {
        logger.info("Executing payment with paymentId: {} and payerId: {}", paymentId, payerId);
        Payment payment = new Payment();
        payment.setId(paymentId);

        PaymentExecution paymentExecution = new PaymentExecution();
        paymentExecution.setPayerId(payerId);
        try {
            Payment executedPayment = payment.execute(apiContext, paymentExecution);
            logger.info("Payment executed successfully: {}", executedPayment);
            return executedPayment;
        } catch (PayPalRESTException e) {
            logger.error("Failed to execute PayPal payment", e);
            throw new PayPalException("Failed to execute PayPal payment", e);
        }
    }
}
