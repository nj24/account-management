package com.project.account.management.api.model;

import java.math.BigDecimal;
import java.util.UUID;

import static java.util.Objects.requireNonNull;

public record UpdateBalanceRequest(UUID accountId, BigDecimal balance) {
    public UpdateBalanceRequest {
        requireNonNull(accountId);
        requireNonNull(balance);
    }
}
