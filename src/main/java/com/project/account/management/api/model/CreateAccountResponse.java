package com.project.account.management.api.model;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public record CreateAccountResponse(UUID accountRef, String accountName, BigDecimal balance) {
    public CreateAccountResponse {
        Objects.requireNonNull(accountRef);
        Objects.requireNonNull(accountName);
        Objects.requireNonNull(balance);
    }
}
