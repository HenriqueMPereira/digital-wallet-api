package com.github.henriquempereira.digitalwalletapi.exception;

/**
 * Thrown when an invalid amount is provided for a transaction, such as a negative or zero value.
 */
public class InvalidAmountException extends RuntimeException {
    public InvalidAmountException(String message) {
        super(message);
    }
}
