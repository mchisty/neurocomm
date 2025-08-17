package com.gpayments.cardmanagement.exception;

public class CardManagementException extends RuntimeException {
    public CardManagementException(String message) {
        super(message);
    }
    
    public CardManagementException(String message, Throwable cause) {
        super(message, cause);
    }
}
