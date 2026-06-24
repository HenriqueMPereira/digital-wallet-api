package com.github.henriquempereira.digitalwalletapi.wallet;

import java.math.BigDecimal;

public record WalletResponse(
        Long id,
        String name,
        BigDecimal balance
){}