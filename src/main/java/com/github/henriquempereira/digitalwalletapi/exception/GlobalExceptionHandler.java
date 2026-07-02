package com.github.henriquempereira.digitalwalletapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Translates domain exceptions raised across the API into HTTP responses.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles an attempt to create a wallet with a CPF that already exists.
     * @param ex the raised exception
     * @return a {@code 409 Conflict} response with the exception message
     */
    @ExceptionHandler(CpfAlreadyExistsException.class)
    public ResponseEntity<String> handleCpfAlreadyExistsException(CpfAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    /**
     * Handles a lookup for a wallet that does not exist.
     * @param ex the raised exception
     * @return a {@code 404 Not Found} response with the exception message
     */
    @ExceptionHandler(WalletNotFoundException.class)
    public ResponseEntity<String> handleWalletNotFoundException(WalletNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    /**
     * Handles a transfer attempted between the same source and destination wallet.
     * @param ex the raised exception
     * @return a {@code 400 Bad Request} response with the exception message
     */
    @ExceptionHandler(SameWalletTransferException.class)
    public ResponseEntity<String> handleSameWalletTransferException(SameWalletTransferException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    /**
     * Handles invalid arguments raised by domain logic, such as non-positive amounts.
     * @param ex the raised exception
     * @return a {@code 400 Bad Request} response with the exception message
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    /**
     * Handles illegal state exceptions raised by domain logic.
     * @param ex the raised exception
     * @return a {@code 400 Bad Request} response with the exception message
     */
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<String> handleIllegalStateException(IllegalStateException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    /**
     * Handles a concurrent modification conflict detected by optimistic locking.
     * @param ex the raised exception
     * @return a {@code 409 Conflict} response instructing the client to retry
     */
    @ExceptionHandler(ObjectOptimisticLockingFailureException.class)
    public ResponseEntity<String> handleOptimisticLockingException(ObjectOptimisticLockingFailureException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body("Conflict occurred due to concurrent modification. Please try again.");
    }
}