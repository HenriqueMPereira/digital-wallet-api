package com.github.henriquempereira.digitalwalletapi.wallet;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

/**
 * Payload for depositing an amount into a wallet.
 * @param amount the amount to credit, must be greater than zero
 */
public record DepositRequest(
        @NotNull @Positive BigDecimal amount
){}
