package com.project.account.management.exception;

public class InsufficientBalanceException extends RuntimeException {
    public InsufficientBalanceException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s  %s : '%s' does not have sufficient balance", resourceName, fieldName, fieldValue));
    }
}
