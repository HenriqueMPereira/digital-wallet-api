package com.github.henriquempereira.digitalwalletapi.transaction;

import com.github.henriquempereira.digitalwalletapi.wallet.Wallet;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Represents a financial transaction.
 * <p>
 * This entity maps transaction records to the database using JPA. It captures the
 * transaction type, the involved wallets (source and destination), the amount transferred,
 * and the exact time the transaction took place.
 */
@Entity
@Getter
@NoArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Transaction type cannot be null")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType type;

    @JoinColumn(name = "source_wallet_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Wallet sourceWallet;

    @JoinColumn(name = "destination_wallet_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Wallet destinationWallet;

    @NotNull(message = "Amount cannot be null")
    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public Transaction(TransactionType type, Wallet sourceWallet, Wallet destinationWallet, BigDecimal amount) {
        this.type = type;
        this.sourceWallet = sourceWallet;
        this.destinationWallet = destinationWallet;
        this.amount = amount;
        this.createdAt = LocalDateTime.now();
    }
}
