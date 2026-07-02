package com.github.henriquempereira.digitalwalletapi.wallet;

import com.github.henriquempereira.digitalwalletapi.exception.CpfAlreadyExistsException;
import com.github.henriquempereira.digitalwalletapi.exception.WalletNotFoundException;
import com.github.henriquempereira.digitalwalletapi.transaction.Transaction;
import com.github.henriquempereira.digitalwalletapi.transaction.TransactionRepository;
import com.github.henriquempereira.digitalwalletapi.transaction.TransactionType;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Handles wallet lifecycle operations such as creation, lookup, and balance deposits.
 */
@Service
@RequiredArgsConstructor
public class WalletService {

    private final WalletRepository repository;
    private final TransactionRepository transactionRepository;

    /**
     * Creates and persists a new wallet.
     * @param request the wallet name and CPF to register
     * @return the created wallet
     * @throws CpfAlreadyExistsException if a wallet already exists for the given CPF
     */
    @Transactional
    public WalletResponse createWallet(CreateWalletRequest request){
        if(repository.existsByCpf(request.cpf())){
            throw new CpfAlreadyExistsException("Wallet with this CPF already exists");
        }
        Wallet wallet = new Wallet(request.name(), request.cpf());
        repository.save(wallet);
        return new WalletResponse(
                wallet.getId(),
                wallet.getName(),
                wallet.getBalance());
    }

    /**
     * Retrieves a wallet by its id.
     * @param id the wallet id
     * @return the matching wallet
     * @throws WalletNotFoundException if no wallet exists with the given id
     */
    public WalletResponse getWallet(Long id) {
        return repository.findById(id).
                map(wallet -> new WalletResponse(wallet.getId(), wallet.getName(), wallet.getBalance()))
                .orElseThrow(() -> new WalletNotFoundException(String.format("Wallet %d not found", id)));
    }

    /**
     * Credits an amount into a wallet's balance and records the corresponding deposit transaction.
     * @param id the id of the wallet to credit
     * @param request the amount to deposit
     * @return the wallet with its updated balance
     * @throws WalletNotFoundException if no wallet exists with the given id
     */
    @Transactional
    public WalletResponse deposit(Long id, DepositRequest request) {
        Wallet wallet = repository.findById(id)
                .orElseThrow(() -> new WalletNotFoundException(String.format("Wallet %d not found", id)));
        wallet.credit(request.amount());
        // Explicit save for clarity, even though dirty checking would handle this
        repository.save(wallet);
        Transaction transaction = new Transaction(TransactionType.DEPOSIT, wallet, null, request.amount());
        transactionRepository.save(transaction);
        return new WalletResponse(
                wallet.getId(),
                wallet.getName(),
                wallet.getBalance());
    }
}
