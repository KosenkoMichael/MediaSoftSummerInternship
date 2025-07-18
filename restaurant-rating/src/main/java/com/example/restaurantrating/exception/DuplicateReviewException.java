package com.example.restaurantrating.exception;

public class DuplicateReviewException extends RuntimeException {
    public DuplicateReviewException(String msg) {
        super(msg);
    }
}
