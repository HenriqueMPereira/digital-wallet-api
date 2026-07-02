package com.github.henriquempereira.digitalwalletapi.exception;

/**
 * Thrown when attempting to create a wallet with a CPF that is already registered.
 */
public class CpfAlreadyExistsException extends RuntimeException {
    public CpfAlreadyExistsException(String message) {
        super(message);
    }
}
