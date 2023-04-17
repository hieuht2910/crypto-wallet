package com.hieu.crypto.service.core.impl;

import com.hieu.crypto.entity.Currency;
import com.hieu.crypto.entity.CurrencyPair;
import com.hieu.crypto.model.PairDefinition;
import com.hieu.crypto.repository.CurrencyPairRepository;
import com.hieu.crypto.service.core.CurrencyPairService;
import com.hieu.crypto.service.core.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class CurrencyPairServiceImpl implements CurrencyPairService {

    @Autowired
    private CurrencyService currencyService;
    @Autowired
    private CurrencyPairRepository currencyPairRepository;

    @Override
    public CurrencyPair getBy(PairDefinition pairDefinition) {
        String base = pairDefinition.getBase();
        String quote = pairDefinition.getQuote();

        Currency baseCurrency = currencyService.findBySymbol(base);
        Currency quoteCurrency = currencyService.findBySymbol(quote);

        if (Objects.nonNull(baseCurrency) && Objects.nonNull(quoteCurrency)) {
            CurrencyPair currencyPair = new CurrencyPair();
            currencyPair.setSymbol(pairDefinition.getSymbol());
            currencyPair.setBaseCurrency(baseCurrency);
            currencyPair.setQuoteCurrency(quoteCurrency);
            return currencyPair;
        }
        return null;
    }

    @Override
    public CurrencyPair findBySymbol(String symbol) {
        return currencyPairRepository.findBySymbol(symbol);
    }

    @Override
    public List<CurrencyPair> findAll() {
        return currencyPairRepository.findAll();
    }

    @Override
    public CurrencyPair save(CurrencyPair pair) {
        if (Objects.nonNull(pair))
            return currencyPairRepository.save(pair);
        return null;
    }
}
