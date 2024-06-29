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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class PaypalServiceImpl implements PaypalService {

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
        Payment payment = new Payment();
        payment.setIntent("SALE");
        payment.setPayer(getPayer(userId, method));

        BigDecimal discount = orderService.calculateDiscount(userId);
        BigDecimal totalCost = orderService.calculateTotal(userId);

        payment.setTransactions(getTransactions(currency, totalCost.subtract(discount)));
        payment.setRedirectUrls(getRedirectUrls(successUrl, cancelUrl));

        try {
            return payment.create(apiContext);
        } catch (PayPalRESTException e) {
            throw new PayPalException("Failed to create PayPal payment", e);
        }
    }

    private Payer getPayer(String userId, String method) {
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

    private List<Transaction> getTransactions(String currency, BigDecimal total) {
        Amount amount = new Amount();
        amount.setCurrency(String.valueOf(currency));
        amount.setTotal(String.format(Locale.forLanguageTag(String.valueOf(currency)), "%.2f", total));

        Transaction transaction = new Transaction();
        transaction.setDescription("description of purchase");
        transaction.setAmount(amount);

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);
        return transactions;
    }

    private RedirectUrls getRedirectUrls(String successUrl, String cancelUrl) {
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
        Payment payment = new Payment();
        payment.setId(paymentId);

        PaymentExecution paymentExecution = new PaymentExecution();
        paymentExecution.setPayerId(payerId);
        try {
            return payment.execute(apiContext, paymentExecution);
        } catch (PayPalRESTException e) {
            throw new PayPalException("Failed to create PayPal payment", e);
        }
    }
}
