package com.github.henriquempereira.digitalwalletapi.transaction;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

/**
 * Payload for transferring an amount between two wallets.
 * @param sourceWalletId the wallet to debit
 * @param destinationWalletId the wallet to credit
 * @param amount the amount to transfer, must be greater than zero
 */
public record TransferRequest(
        @NotNull Long sourceWalletId,
        @NotNull Long destinationWalletId,
        @NotNull @Positive BigDecimal amount
){}
