package com.github.henriquempereira.digitalwalletapi.exception;

public class WalletNotFoundException extends RuntimeException {
  public WalletNotFoundException(String message) {
    super(message);
  }
}
