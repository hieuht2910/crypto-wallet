package com.hieu.crypto.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data @AllArgsConstructor @NoArgsConstructor
public class WalletCurrencyModel {

    private String symbol;
    private BigDecimal balance;
}
