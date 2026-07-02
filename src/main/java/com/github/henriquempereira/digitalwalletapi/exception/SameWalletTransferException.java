package com.github.henriquempereira.digitalwalletapi.exception;

/**
 * Thrown when a transfer is attempted between the same source and destination wallet.
 */
public class SameWalletTransferException extends RuntimeException {
    public SameWalletTransferException(String message) {
        super(message);
    }
}
