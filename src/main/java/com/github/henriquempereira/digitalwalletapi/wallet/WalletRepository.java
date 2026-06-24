package com.github.henriquempereira.digitalwalletapi.wallet;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {

    public boolean existsByCpf(String cpf);
}
