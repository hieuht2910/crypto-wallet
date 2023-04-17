package com.hieu.crypto.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name = "currency_pair", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"symbol"})
})
@Data
public class CurrencyPair {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String symbol;

    @Column(name = "bid_price")
    private BigDecimal bidPrice;

    @Column(name = "ask_price")
    private BigDecimal askPrice;

    @ManyToOne
    @JoinColumn(name = "base_currency_id")
    @JsonIgnore
    private Currency baseCurrency;

    @ManyToOne
    @JoinColumn(name = "quote_currency_id")
    @JsonIgnore
    private Currency quoteCurrency;
}

