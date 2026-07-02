package com.github.henriquempereira.digitalwalletapi.transaction;

import com.github.henriquempereira.digitalwalletapi.exception.SameWalletTransferException;
import com.github.henriquempereira.digitalwalletapi.exception.WalletNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller exposing the endpoint for wallet-to-wallet transfers.
 */
@RestController
@RequestMapping("/transfers")
@RequiredArgsConstructor
public class TransferController {

    private final TransferService service;

    /**
     * Transfers an amount from one wallet to another.
     * @param request the source wallet, destination wallet, and amount to transfer
     * @return the resulting transfer transaction
     * @throws SameWalletTransferException {@code 400 Bad Request} if the source and destination wallets are the same
     * @throws WalletNotFoundException {@code 404 Not Found} if the source or destination wallet does not exist
     * @throws IllegalStateException {@code 400 Bad Request} if the source wallet has insufficient balance
     * @throws ObjectOptimisticLockingFailureException {@code 409 Conflict} if either wallet was concurrently
     *         modified between being read and saved
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TransferResponse transfer(@Valid @RequestBody TransferRequest request) {
        return service.transfer(request);
    }
}
