package com.github.henriquempereira.digitalwalletapi.exception;

public class SameWalletTransferException extends RuntimeException {
    public SameWalletTransferException(String message) {
        super(message);
    }
}
