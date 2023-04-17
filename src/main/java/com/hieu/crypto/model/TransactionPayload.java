package com.hieu.crypto.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionPayload {
    private String username;
    private String symbol;
    private BigDecimal quantity;
    private String type;
}
