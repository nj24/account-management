package com.project.account.management.api.model;

import java.math.BigDecimal;
import java.util.UUID;

import static java.util.Objects.requireNonNull;

public record TransferBalanceRequest(UUID sourceAccountRef, UUID destinationAccountRef, BigDecimal amountToTransfer) {
    public TransferBalanceRequest {
        requireNonNull(sourceAccountRef);
        requireNonNull(destinationAccountRef);
        requireNonNull(amountToTransfer);
    }
}
