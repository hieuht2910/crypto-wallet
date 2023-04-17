package com.hieu.crypto.service.price;

import com.hieu.crypto.entity.CurrencyPair;
import com.hieu.crypto.model.TradingPair;
import com.hieu.crypto.repository.UserRepository;
import com.hieu.crypto.service.BinancePriceAggregator;
import com.hieu.crypto.service.core.CurrencyPairService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class PriceAggregator {
    @Autowired
    private CurrencyPairService currencyPairService;

    @Value("${configurations.priceAggregator.interval}")
    private String interval;

    @Autowired
    private BinancePriceAggregator binancePriceAggregator;

    @Autowired
    private HuobiPriceAggregator huobiPriceAggregator;

    @PostConstruct
    public void init() {
        log.debug("interval: " + interval);
    }

    @Scheduled(fixedDelayString = "${configurations.priceAggregator.interval}")
    public void Prices() throws JSONException {
        List<TradingPair> tradingPairs = getTradingPairs();
        List<TradingPair> binancePairs = binancePriceAggregator.getPrices(tradingPairs);
        List<TradingPair> huobiPairs = huobiPriceAggregator.getPrices(tradingPairs);
        getBestPrices(tradingPairs, binancePairs, huobiPairs);

        log.info("Aggregate Trading pairs: " + tradingPairs);

        tradingPairs.stream().forEach(pair -> {
            CurrencyPair currencyPair = currencyPairService.findBySymbol(pair.getSymbol());
            currencyPair.setAskPrice(pair.getAsk());
            currencyPair.setBidPrice(pair.getBid());
            currencyPairService.save(currencyPair);
        });
    }

    private void getBestPrices(List<TradingPair> originPairs, List<TradingPair> binancePairs, List<TradingPair> huobiPairs) {
        originPairs.stream().forEach(originPair -> {
            BigDecimal binanceAsk = binancePairs.stream().filter(binancePair -> binancePair.getSymbol().equalsIgnoreCase(originPair.getSymbol())).findFirst().get().getAsk();
            BigDecimal binanceBid = binancePairs.stream().filter(binancePair -> binancePair.getSymbol().equalsIgnoreCase(originPair.getSymbol())).findFirst().get().getBid();
            BigDecimal huobiAsk = huobiPairs.stream().filter(huobiPair -> huobiPair.getSymbol().equalsIgnoreCase(originPair.getSymbol())).findFirst().get().getAsk();
            BigDecimal huobiBid = huobiPairs.stream().filter(huobiPair -> huobiPair.getSymbol().equalsIgnoreCase(originPair.getSymbol())).findFirst().get().getBid();

            originPair.setBid(binanceBid.max(huobiBid));
            originPair.setAsk(binanceAsk.min(huobiAsk));
        });
    }

    private List<TradingPair> getTradingPairs() {
        List<TradingPair> tradingPairs = new ArrayList<>();
        List<CurrencyPair> currencyPairs = currencyPairService.findAll();
        for (CurrencyPair currencyPair : currencyPairs) {
            TradingPair tradingPair = new TradingPair();
            tradingPair.setSymbol(currencyPair.getSymbol());
            tradingPair.setBase(currencyPair.getBaseCurrency().getSymbol());
            tradingPair.setQuote(currencyPair.getQuoteCurrency().getSymbol());
            tradingPairs.add(tradingPair);
        }
        return tradingPairs;
    }
}
