package com.hieu.crypto.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

@Data @AllArgsConstructor @NoArgsConstructor
public class TradingPair implements Cloneable {

    private String base;
    private String quote;
    private String symbol;
    private String source;
    private BigDecimal bid;
    private BigDecimal ask;

    @Override
    public TradingPair clone() {
        try {
            return (TradingPair) super.clone();
        } catch (CloneNotSupportedException e) {
            return new TradingPair(base, quote, symbol, source, bid, ask);
        }
    }
}
