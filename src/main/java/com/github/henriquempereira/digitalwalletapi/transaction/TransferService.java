package com.github.henriquempereira.digitalwalletapi.transaction;

import com.github.henriquempereira.digitalwalletapi.exception.SameWalletTransferException;
import com.github.henriquempereira.digitalwalletapi.exception.WalletNotFoundException;
import com.github.henriquempereira.digitalwalletapi.wallet.Wallet;
import com.github.henriquempereira.digitalwalletapi.wallet.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TransferService {

    private final TransactionRepository transactionRepository;
    private final WalletRepository walletRepository;

    @Transactional
    public TransferResponse transfer(TransferRequest request) {
        if(request.sourceWalletId().equals(request.destinationWalletId())) {
            throw new SameWalletTransferException("Source and destination wallets must be different");
        }
        Wallet sourceWallet = walletRepository.findById(request.sourceWalletId())
                .orElseThrow(() -> new WalletNotFoundException(String.format("Source wallet %d not found", request.sourceWalletId())));
        Wallet destinationWallet = walletRepository.findById(request.destinationWalletId())
                .orElseThrow(() -> new WalletNotFoundException(String.format("Destination wallet %d not found", request.destinationWalletId())));
        sourceWallet.debit(request.amount());
        destinationWallet.credit(request.amount());
        // Explicit save for clarity, even though dirty checking would handle this
        walletRepository.save(sourceWallet);
        walletRepository.save(destinationWallet);
        Transaction transaction = new Transaction(TransactionType.TRANSFER, sourceWallet, destinationWallet, request.amount());
        transactionRepository.save(transaction);
        return new TransferResponse(
                transaction.getId(),
                transaction.getType(),
                sourceWallet.getId(),
                destinationWallet.getId(),
                transaction.getAmount());
    }
}
