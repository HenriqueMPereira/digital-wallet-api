package com.github.henriquempereira.digitalwalletapi.wallet;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

/**
 * Payload for creating a new wallet.
 * @param name the wallet owner's name
 * @param cpf the wallet owner's CPF
 */
public record CreateWalletRequest(
        @NotBlank(message = "Name cannot be blank")
        String name,

        @NotBlank(message = "CPF cannot be blank")
        @CPF(message = "Invalid CPF")
        String cpf
){}
