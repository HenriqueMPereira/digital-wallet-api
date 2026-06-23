package com.github.henriquempereira.digitalwalletapi.wallet;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Represents a digital wallet with a name, CPF, balance, and creation timestamp.
 */
@Entity
@Getter
@NoArgsConstructor
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @CPF(message = "Invalid CPF")
    @NotNull
    @Column(unique = true)
    private String cpf;

    @NotNull
    private BigDecimal balance;

    @Version
    private Long version;

    private LocalDateTime createdAt;

    public Wallet(String name, String cpf) {
        this.name = name;
        this.cpf = cpf;
        this.createdAt = LocalDateTime.now();
        this.balance = BigDecimal.ZERO;
    }


    /**
     * Credits the specified amount into the wallet.
     * @param amount the amount to credit
     * @throws IllegalArgumentException if the amount is less than or equal to zero
     */
    public void credit(BigDecimal amount) {
        validateAmount(amount);
        this.balance = this.balance.add(amount);
    }

    /**
     * Debits the specified amount from the wallet.
     * @param amount the amount to debit
     * @throws IllegalArgumentException if the amount is less than or equal to zero
     * @throws IllegalStateException if the balance is insufficient for the debit operation
     */
    public void debit(BigDecimal amount) {
        validateAmount(amount);
        if (this.balance.compareTo(amount) < 0) {
            throw new IllegalStateException("Insufficient balance for debit operation.");
        }
        this.balance = this.balance.subtract(amount);
    }

    /**
     * Validates that the specified amount is greater than zero.
     * @param amount the amount to validate
     * @throws IllegalArgumentException if the amount is less than or equal to zero or null
     */
    private void validateAmount(BigDecimal amount) {
        if(amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero.");
        }
    }
}
