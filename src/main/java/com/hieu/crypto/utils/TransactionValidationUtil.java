package com.hieu.crypto.utils;

import com.hieu.crypto.entity.Balance;
import com.hieu.crypto.entity.CurrencyPair;
import com.hieu.crypto.entity.Transaction;
import com.hieu.crypto.entity.User;
import com.hieu.crypto.model.TransactionPayload;

import java.math.BigDecimal;
import java.util.Objects;

public class TransactionValidationUtil {

    private TransactionValidationUtil() {
    }

    public static void doPayloadValidation(TransactionPayload transactionPayload) {
        if (Objects.isNull(transactionPayload))
            throw new RuntimeException("Transaction payload is null");

        if (Objects.isNull(transactionPayload.getUsername()))
            throw new RuntimeException("Username is null");

        if (Objects.isNull(transactionPayload.getSymbol()))
            throw new RuntimeException("Symbol is null");

        if (Objects.isNull(transactionPayload.getQuantity()))
            throw new RuntimeException("Quantity is null");

        if (Objects.isNull(transactionPayload.getType()))
            throw new RuntimeException("Type is null");
    }

    public static void doBusinessValidation(TransactionPayload transactionPayload, User user, CurrencyPair pair, Transaction.Type type) {
        if (Objects.isNull(user))
            throw new RuntimeException("User not found");
        if (Objects.isNull(pair))
            throw new RuntimeException("Currency pair not found");
        if (transactionPayload.getQuantity().compareTo(BigDecimal.ZERO) <= 0)
            throw new RuntimeException("Quantity must be greater than 0");
    }

    public static void doBalanceValidation(Balance baseBalance, Balance quoteBalance, Transaction.Type type, BigDecimal quantity, BigDecimal price) {
        if (type == Transaction.Type.BUY) {
            if (quoteBalance.getQuantity().compareTo(price) < 0)
                throw new RuntimeException("Insufficient balance for " + quoteBalance.getCurrency().getSymbol());
        } else {
            if (baseBalance.getQuantity().compareTo(quantity) < 0)
                throw new RuntimeException("Insufficient balance for " + baseBalance.getCurrency().getSymbol());
        }
    }
}
