package com.github.henriquempereira.digitalwalletapi.wallet;

import com.github.henriquempereira.digitalwalletapi.exception.CpfAlreadyExistsException;
import com.github.henriquempereira.digitalwalletapi.exception.WalletNotFoundException;
import com.github.henriquempereira.digitalwalletapi.transaction.Transaction;
import com.github.henriquempereira.digitalwalletapi.transaction.TransactionRepository;
import com.github.henriquempereira.digitalwalletapi.transaction.TransactionType;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WalletService {

    private final WalletRepository repository;
    private final TransactionRepository transactionRepository;

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

    public WalletResponse getWallet(Long id) {
        return repository.findById(id).
                map(wallet -> new WalletResponse(wallet.getId(), wallet.getName(), wallet.getBalance()))
                .orElseThrow(() -> new WalletNotFoundException(String.format("Wallet %d not found", id)));
    }

    @Transactional
    public WalletResponse addBalance(Long id, DepositRequest request) {
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
