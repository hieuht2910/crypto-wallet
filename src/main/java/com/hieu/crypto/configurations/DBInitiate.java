package com.hieu.crypto.configurations;

import com.hieu.crypto.entity.Currency;
import com.hieu.crypto.repository.CurrencyRepository;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class DBInitiate {

    @Autowired
    private DBInitiateUser dbInitiateUser;
    @Autowired
    private DBInitiateCurrency dbInitiateCurrency;
    @Autowired
    private DBInitiateCurrencyPair dbInitiateCurrencyPair;

    @PostConstruct
    public void init() throws IOException {
        log.info("DBInitiate init");
        dbInitiateCurrency.initCurrency();
        dbInitiateCurrencyPair.initCurrencyPairs();
        dbInitiateUser.initUser();


    }
}
