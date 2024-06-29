package com.lalabrand.ecommerce.exception;

public class PayPalException extends RuntimeException {

    public PayPalException(String message, Throwable cause) {
        super(message, cause);
    }
}

