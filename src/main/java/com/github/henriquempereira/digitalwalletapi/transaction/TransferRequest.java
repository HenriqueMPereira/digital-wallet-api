package com.github.henriquempereira.digitalwalletapi.transaction;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record TransferRequest(
        @NotNull Long sourceWalletId,
        @NotNull Long destinationWalletId,
        @NotNull @Positive BigDecimal amount
){}
