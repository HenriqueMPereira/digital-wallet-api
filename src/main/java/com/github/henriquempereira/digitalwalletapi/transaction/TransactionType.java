package com.github.henriquempereira.digitalwalletapi.transaction;

/**
 * The kind of movement a {@link Transaction} represents.
 */
public enum TransactionType {
    /** Balance credited into a wallet from an external source. */
    DEPOSIT,
    /** Balance moved from one wallet to another. */
    TRANSFER
}
