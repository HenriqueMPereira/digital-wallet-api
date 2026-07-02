package com.github.henriquempereira.digitalwalletapi.transaction;

/**
 * Transfer result data returned to API clients.
 * @param id the created transaction's id
 * @param type the transaction type
 * @param sourceWalletId the wallet debited
 * @param destinationWalletId the wallet credited
 * @param amount the transferred amount
 */
public record TransferResponse(
        Long id,
        TransactionType type,
        Long sourceWalletId,
        Long destinationWalletId,
        java.math.BigDecimal amount
){}
