package com.github.henriquempereira.digitalwalletapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.HashMap;

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
    public ProblemDetail handleCpfAlreadyExistsException(CpfAlreadyExistsException ex) {
        return createProblemDetail(
                HttpStatus.CONFLICT,
                ex.getMessage(),
                "CPF Already Exists"
        );
    }

    /**
     * Handles a lookup for a wallet that does not exist.
     * @param ex the raised exception
     * @return a {@code 404 Not Found} response with the exception message
     */
    @ExceptionHandler(WalletNotFoundException.class)
    public ProblemDetail handleWalletNotFoundException(WalletNotFoundException ex) {
        return createProblemDetail(
                HttpStatus.NOT_FOUND,
                ex.getMessage(),
                "Wallet Not Found"
        );
    }

    /**
     * Handles a transfer attempted between the same source and destination wallet.
     * @param ex the raised exception
     * @return a {@code 400 Bad Request} response with the exception message
     */
    @ExceptionHandler(SameWalletTransferException.class)
    public ProblemDetail handleSameWalletTransferException(SameWalletTransferException ex) {
        return createProblemDetail(
                HttpStatus.BAD_REQUEST,
                ex.getMessage(),
                "Same Wallet Transfer"
        );
    }

    /**
     * Handles a concurrent modification conflict detected by optimistic locking.
     * @param ex the raised exception
     * @return a {@code 409 Conflict} response instructing the client to retry
     */
    @ExceptionHandler(ObjectOptimisticLockingFailureException.class)
    public ProblemDetail handleOptimisticLockingException(ObjectOptimisticLockingFailureException ex){
        return createProblemDetail(
                HttpStatus.CONFLICT,
                ex.getMessage(),
                "Conflict"
        );
    }

    /**
     * Handles validation errors raised by {@code @Valid} on request bodies.
     * @param ex the raised exception
     * @return a {@code 400 Bad Request} response with a {@code fieldErrors} map
     *         detailing which fields failed validation and why
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        HashMap<String, String> fieldErrors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
            fieldErrors.put(error.getField(), error.getDefaultMessage()));
        ProblemDetail problemDetail = createProblemDetail(
                HttpStatus.BAD_REQUEST,
                "One or more fields are invalid",
                "Validation Error"
        );
        problemDetail.setProperty("fieldErrors", fieldErrors);
        return problemDetail;
    }

    /**
     *  Handles an attempt to transfer more funds than are available in the source wallet.
     * @param ex the raised exception
     * @return a {@code 400 Bad Request} response with the exception message
     */
    @ExceptionHandler(InsufficientBalanceException.class)
    public ProblemDetail handleInsufficientBalanceException(InsufficientBalanceException ex) {
        return createProblemDetail(
                HttpStatus.BAD_REQUEST,
                ex.getMessage(),
                "Insufficient Balance"
        );
    }

    /**
     * Handles invalid amount exceptions raised by domain logic.
     * @param ex the raised exception
     * @return a {@code 400 Bad Request} response with the exception message
     */
    @ExceptionHandler(InvalidAmountException.class)
    public ProblemDetail handleInvalidAmountException(InvalidAmountException ex) {
        return createProblemDetail(
                HttpStatus.BAD_REQUEST,
                ex.getMessage(),
                "Invalid Amount"
        );
    }

    private ProblemDetail createProblemDetail(HttpStatus status, String message, String title) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, message);
        problemDetail.setTitle(title);
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }
}