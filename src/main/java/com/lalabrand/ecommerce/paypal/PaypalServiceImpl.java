package com.lalabrand.ecommerce.paypal;

import com.lalabrand.ecommerce.exception.PayPalException;
import com.lalabrand.ecommerce.user.User;
import com.lalabrand.ecommerce.user.UserRepository;
import com.lalabrand.ecommerce.user.UserService;
import com.lalabrand.ecommerce.user.cart.CartDTO;
import com.lalabrand.ecommerce.user.cart.CartService;
import com.lalabrand.ecommerce.user.cart.cart_item.CartItem;
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
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PaypalServiceImpl implements PaypalService {

    private final CartService cartService;
    private final UserService userService;
    private final UserRepository userRepository;
    private APIContext apiContext;

    @Autowired
    public PaypalServiceImpl(CartService cartService, UserService userService, UserRepository userRepository, APIContext apiContext) {
        this.cartService = cartService;
        this.userRepository = userRepository;
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
        CartDTO cartDto = cartService.findCartByUserId(userId).orElseThrow(
                () -> new EntityNotFoundException("Cart hasn't been found for user ( it's empty )")
        );
        Set<CartItem> cartItems = cartDto.toEntity(userRepository.getReferenceById(userId)).getCartItems();
        BigDecimal total = cartItems.stream()
                .map(cartItem -> cartItem.getItem().getPrice().multiply(BigDecimal.valueOf(cartItem.getCount())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Payment payment = new Payment();
        payment.setIntent("SALE");
        payment.setPayer(getPayer(userId, method));
        payment.setTransactions(getTransactions(currency, total));
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
/*        Details details = new Details();
        details.setFee(order.getShipping().getShippingOption().getPrice().setScale(2, RoundingMode.HALF_UP).toString());
        details.setTax("%.2f", order);
        details.setShipping(order.getShipping().getAddress1() + "," + order.getShipping().getAddress2() + ","
                + order.getShipping().getCity() + "," + order.getShipping().getZip());*/

        Amount amount = new Amount();
        amount.setCurrency(String.valueOf(currency));
        amount.setTotal(String.format(Locale.forLanguageTag(String.valueOf(currency)), "%.2f", total));
/*        amount.setDetails(details);*/

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
