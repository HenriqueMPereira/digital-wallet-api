package com.github.henriquempereira.digitalwalletapi.exception;

/**
 * Thrown when a wallet cannot be found for a given id.
 */
public class WalletNotFoundException extends RuntimeException {
  public WalletNotFoundException(String message) {
    super(message);
  }
}
