package com.github.henriquempereira.digitalwalletapi.wallet;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wallets")
@RequiredArgsConstructor
public class WalletController {

    private final WalletService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public WalletResponse createWallet(@Valid @RequestBody CreateWalletRequest request){
        return service.createWallet(request);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public WalletResponse getWallet(@PathVariable Long id){
        return service.getWallet(id);
    }
}
