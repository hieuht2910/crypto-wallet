package com.hieu.crypto.configurations;

import com.hieu.crypto.entity.Currency;
import com.hieu.crypto.entity.CurrencyPair;
import com.hieu.crypto.model.PairDefinition;
import com.hieu.crypto.repository.CurrencyRepository;
import com.hieu.crypto.service.core.CurrencyPairService;
import com.hieu.crypto.service.core.CurrencyService;
import com.hieu.crypto.service.price.PairDefinitionService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class DBInitiateCurrencyPair {

    @Autowired
    private CurrencyPairService currencyPairService;
    @Autowired
    private PairDefinitionService pairDefinitionService;

    public void initCurrencyPairs() throws IOException {
        log.info("DBInitiateCurrencyPair init");

        List<PairDefinition> pairs = pairDefinitionService.readPairs();
        for (PairDefinition pair : pairs) {
            CurrencyPair currencyPair = currencyPairService.getBy(pair);
            currencyPairService.save(currencyPair);
        }
    }
}
