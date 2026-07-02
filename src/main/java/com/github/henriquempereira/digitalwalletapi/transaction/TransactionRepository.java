package com.github.henriquempereira.digitalwalletapi.transaction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for {@link Transaction} persistence operations.
 */
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
