package com.hieu.crypto.service.core;

import com.hieu.crypto.entity.Currency;

public interface CurrencyService {

    Currency findBySymbol(String symbol);

    Currency save(Currency currency);
}
