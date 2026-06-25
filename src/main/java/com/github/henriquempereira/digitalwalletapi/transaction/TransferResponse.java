package com.github.henriquempereira.digitalwalletapi.transaction;

public record TransferResponse(
        Long id,
        TransactionType type,
        Long sourceWalletId,
        Long destinationWalletId,
        java.math.BigDecimal amount
){}
