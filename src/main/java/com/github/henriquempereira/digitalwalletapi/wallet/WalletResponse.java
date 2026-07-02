package com.github.henriquempereira.digitalwalletapi.wallet;

import java.math.BigDecimal;

/**
 * Wallet data returned to API clients.
 * @param id the wallet id
 * @param name the wallet owner's name
 * @param balance the wallet's current balance
 */
public record WalletResponse(
        Long id,
        String name,
        BigDecimal balance
){}