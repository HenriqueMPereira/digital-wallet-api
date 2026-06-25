package com.github.henriquempereira.digitalwalletapi.wallet;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record DepositRequest(
        @NotBlank @Positive BigDecimal amount
){}
