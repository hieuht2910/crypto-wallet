package com.hieu.crypto.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transaction")
@Data
public class Transaction {

    public enum Type {
        BUY, SELL
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", updatable = false)
    @JsonIgnore
    private User user;

    @ManyToOne
    @JoinColumn(name = "currency_pair_id")
    private CurrencyPair currencyPair;

    @ManyToOne
    @JoinColumn(name = "base_balance_id")
    @JsonIgnore
    private Balance baseBalance;

    @ManyToOne
    @JoinColumn(name = "quote_balance_id")
    @JsonIgnore
    private Balance quoteBalance;

    @Enumerated(EnumType.STRING)
    private Type type;

    private BigDecimal price;

    private BigDecimal quantity;

    private LocalDateTime timestamp;
}
