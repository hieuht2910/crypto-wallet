package com.hieu.crypto.configurations;

import com.hieu.crypto.entity.Currency;
import com.hieu.crypto.model.PairDefinition;
import com.hieu.crypto.repository.CurrencyRepository;
import com.hieu.crypto.service.core.CurrencyService;
import com.hieu.crypto.service.price.PairDefinitionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class DBInitiateCurrency {

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private PairDefinitionService pairDefinitionService;

    public void initCurrency() throws IOException {
        log.info("DBInitiateCurrency init");
        List<PairDefinition> pairs = pairDefinitionService.readPairs();
        for (PairDefinition pair : pairs) {
            String base = pair.getBase();
            String quote = pair.getQuote();
            storeCurrency(base);
            storeCurrency(quote);
        }
    }

    private void storeCurrency(String symbol) {
        Currency existing = currencyService.findBySymbol(symbol);
        if (Objects.isNull(existing)) {
            Currency currency = new Currency(symbol);
            currencyService.save(currency);
        }
    }
}
