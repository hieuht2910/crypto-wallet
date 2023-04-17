package com.hieu.crypto.service;

import com.hieu.crypto.model.PairDefinition;
import com.hieu.crypto.model.TradingPair;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.json.JSONArray;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BinancePriceAggregator {

    private static final String SOURCE = "BINANCE";

    @Value("${configurations.priceAggregator.binanceUrl}")
    private String binanceUrl;

    public List<TradingPair> getPrices(List<TradingPair> originPairs) throws JSONException {
        List<TradingPair> pairs = originPairs.stream().map(TradingPair::clone).collect(Collectors.toList());
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> binanceResponse = restTemplate.getForEntity(binanceUrl, String.class);
        JSONArray binanceJsonArray = new JSONArray(binanceResponse.getBody());
        for (int i = 0; i < binanceJsonArray.length(); i++) {
            JSONObject jsonObject = binanceJsonArray.getJSONObject(i);
            String symbol = jsonObject.getString("symbol");
            TradingPair pair = pairs.stream().filter(p -> p.getSymbol().equals(symbol)).findFirst().orElse(null);
            if (Objects.nonNull(pair)) {
                BigDecimal ask = new BigDecimal(jsonObject.getString("askPrice"));
                BigDecimal bid = new BigDecimal(jsonObject.getString("bidPrice"));
                pair.setSource(SOURCE);
                pair.setBid(bid);
                pair.setAsk(ask);
            }
        }
        log.debug("Getting prices from Binance: {}", pairs);
        return pairs;
    }
}
