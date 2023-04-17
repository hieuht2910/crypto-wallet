package com.hieu.crypto.service.core;

import com.hieu.crypto.entity.Currency;
import com.hieu.crypto.entity.CurrencyPair;
import com.hieu.crypto.model.PairDefinition;

import java.util.List;

public interface CurrencyPairService {

    CurrencyPair getBy(PairDefinition pairDefinition);

    CurrencyPair findBySymbol(String symbol);

    List<CurrencyPair> findAll();

    CurrencyPair save(CurrencyPair pair);
}
