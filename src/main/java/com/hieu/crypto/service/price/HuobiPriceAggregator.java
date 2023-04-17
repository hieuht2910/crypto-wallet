package com.hieu.crypto.service.price;

import com.hieu.crypto.model.TradingPair;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class HuobiPriceAggregator {

    private static final String SOURCE = "HUOBI";

    @Value("${configurations.priceAggregator.huobiUrl}")
    private String huobiUrl;

    public List<TradingPair> getPrices(List<TradingPair> originPairs) throws JSONException {
        List<TradingPair> pairs = originPairs.stream().map(TradingPair::clone).collect(Collectors.toList());
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> huobiResponse = restTemplate.getForEntity(huobiUrl, String.class);
        JSONObject huobiJsonObject = new JSONObject(huobiResponse.getBody());
        JSONArray huobiJsonArray = huobiJsonObject.getJSONArray("data");

        for (int i = 0; i < huobiJsonArray.length(); i++) {
            JSONObject jsonObject = huobiJsonArray.getJSONObject(i);
            String symbol = jsonObject.getString("symbol");
            TradingPair pair = pairs.stream().filter(p -> p.getSymbol().equalsIgnoreCase(symbol)).findFirst().orElse(null);
            if (Objects.nonNull(pair)) {
                BigDecimal ask = new BigDecimal(jsonObject.getString("ask"));
                BigDecimal bid = new BigDecimal(jsonObject.getString("bid"));
                pair.setSource(SOURCE);
                pair.setBid(bid);
                pair.setAsk(ask);
            }
        }

        log.debug("Getting prices from Huobi: {}", pairs);
        return pairs;
    }
}
