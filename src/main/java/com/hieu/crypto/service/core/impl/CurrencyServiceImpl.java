package com.hieu.crypto.service.core.impl;

import com.hieu.crypto.entity.Currency;
import com.hieu.crypto.repository.CurrencyRepository;
import com.hieu.crypto.service.core.CurrencyService;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CurrencyServiceImpl implements CurrencyService {

    @Autowired
    private CurrencyRepository currencyRepository;

    @Override
    public Currency findBySymbol(String symbol) {
        if (StringUtils.isNotEmpty(symbol))
            return currencyRepository.findBySymbol(symbol);
        return null;
    }

    @Override
    public Currency save(Currency currency) {
        if (Objects.nonNull(currency))
            return currencyRepository.save(currency);
        return null;
    }
}
