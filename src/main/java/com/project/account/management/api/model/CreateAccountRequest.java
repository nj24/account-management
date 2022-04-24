package com.project.account.management.api.model;

import java.math.BigDecimal;

import static java.util.Objects.requireNonNull;

public record CreateAccountRequest(String accountName, BigDecimal initialBalance) {
    public CreateAccountRequest {
        requireNonNull(accountName);
        requireNonNull(initialBalance);
    }
}
