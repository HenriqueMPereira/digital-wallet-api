package com.github.henriquempereira.digitalwalletapi.wallet;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for {@link Wallet} persistence operations.
 */
@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {

    /**
     * Checks whether a wallet already exists for the given CPF.
     * @param cpf the CPF to check
     * @return {@code true} if a wallet with this CPF exists, {@code false} otherwise
     */
    public boolean existsByCpf(String cpf);
}
