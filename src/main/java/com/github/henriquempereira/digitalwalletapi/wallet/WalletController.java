package com.github.henriquempereira.digitalwalletapi.wallet;

import com.github.henriquempereira.digitalwalletapi.exception.CpfAlreadyExistsException;
import com.github.henriquempereira.digitalwalletapi.exception.WalletNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller exposing endpoints for creating and managing wallets.
 */
@RestController
@RequestMapping("/wallets")
@RequiredArgsConstructor
public class WalletController {

    private final WalletService service;

    /**
     * Creates a new wallet.
     * @param request the wallet name and CPF to register
     * @return the created wallet
     * @throws CpfAlreadyExistsException {@code 409 Conflict} if a wallet already exists for the given CPF
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public WalletResponse createWallet(@Valid @RequestBody CreateWalletRequest request){
        return service.createWallet(request);
    }

    /**
     * Retrieves a wallet by its id.
     * @param id the wallet id
     * @return the matching wallet
     * @throws WalletNotFoundException {@code 404 Not Found} if no wallet exists with the given id
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public WalletResponse getWallet(@PathVariable Long id){
        return service.getWallet(id);
    }

    /**
     * Credits an amount into the given wallet's balance.
     * @param id the id of the wallet to credit
     * @param request the amount to deposit
     * @return the wallet with its updated balance
     * @throws WalletNotFoundException {@code 404 Not Found} if no wallet exists with the given id
     * @throws ObjectOptimisticLockingFailureException {@code 409 Conflict} if the wallet was concurrently
     *         modified between being read and saved
     */
    @PostMapping("/{id}/deposits")
    @ResponseStatus(HttpStatus.CREATED)
    public WalletResponse deposit(@PathVariable Long id, @Valid @RequestBody DepositRequest request) {
        return service.deposit(id, request);
    }
}
