package com.project.exceptionhandler;

// When order not found
public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(String message) {
        super(message);
    }


    // When idempotency key already exists
    public static class DuplicateIdempotencyKeyException extends RuntimeException {
        public DuplicateIdempotencyKeyException(String message) {
            super(message);
        }
    }

    // For invalid request data
    public class InvalidOrderException extends RuntimeException {
        public InvalidOrderException(String message) {
            super(message);
        }
    }
}
