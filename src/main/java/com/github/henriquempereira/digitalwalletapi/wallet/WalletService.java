package com.github.henriquempereira.digitalwalletapi.wallet;

import com.github.henriquempereira.digitalwalletapi.exception.CpfAlreadyExistsException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WalletService {

    private final WalletRepository repository;

    @Transactional
    public WalletResponse createWallet(CreateWalletRequest request){
        if(repository.existsByCpf(request.cpf())){
            throw new CpfAlreadyExistsException("Wallet with this CPF already exists");
        }
        Wallet wallet = new Wallet(request.name(), request.cpf());
        repository.save(wallet);
        return new WalletResponse(wallet.getId(), wallet.getName(), wallet.getBalance());
    }
}
