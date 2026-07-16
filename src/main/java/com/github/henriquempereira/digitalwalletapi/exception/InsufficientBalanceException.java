package com.github.henriquempereira.digitalwalletapi.exception;

/**
 * Thrown when a wallet does not have sufficient balance to complete a transaction.
 */
public class InsufficientBalanceException extends RuntimeException {
    public InsufficientBalanceException(String message) {
        super(message);
    }
}
