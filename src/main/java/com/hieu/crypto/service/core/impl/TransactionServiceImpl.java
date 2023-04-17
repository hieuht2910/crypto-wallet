package com.hieu.crypto.service.core.impl;

import com.hieu.crypto.entity.*;
import com.hieu.crypto.model.TransactionPayload;
import com.hieu.crypto.repository.TransactionRepository;
import com.hieu.crypto.service.core.CurrencyPairService;
import com.hieu.crypto.service.core.TransactionService;
import com.hieu.crypto.service.core.UserService;
import com.hieu.crypto.utils.TransactionValidationUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private UserService userService;

    @Autowired
    private CurrencyPairService currencyPairService;

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    @Transactional
    public Transaction doTransaction(TransactionPayload transactionPayload) {
        TransactionValidationUtil.doPayloadValidation(transactionPayload);

        User user = userService.findByUsername(transactionPayload.getUsername());
        CurrencyPair pair = currencyPairService.findBySymbol(transactionPayload.getSymbol());
        Transaction.Type type = Transaction.Type.valueOf(transactionPayload.getType().toUpperCase());

        TransactionValidationUtil.doBusinessValidation(transactionPayload, user, pair, type);
        addNonExistBalance(user, pair);

        BigDecimal price = calculateTotalPrice(pair, type, transactionPayload.getQuantity());
        Balance baseBalance = getBaseBalance(user, pair, type);
        Balance quoteBalance = getQuoteBalance(user, pair, type);

        BigDecimal quantity = transactionPayload.getQuantity();
        TransactionValidationUtil.doBalanceValidation(baseBalance, quoteBalance, type, quantity, price);


        if (type == Transaction.Type.BUY) {
            baseBalance.setQuantity(baseBalance.getQuantity().add(quantity));
            quoteBalance.setQuantity(quoteBalance.getQuantity().subtract(price));
        } else {
            baseBalance.setQuantity(baseBalance.getQuantity().subtract(quantity));
            quoteBalance.setQuantity(quoteBalance.getQuantity().add(price));
        }
        User savedUser = userService.save(user);
        Balance savedBaseBalance = getBaseBalance(savedUser, pair, type);
        Balance savedQuoteBalance = getQuoteBalance(savedUser, pair, type);

        Transaction transaction = new Transaction();
        transaction.setUser(user);
        transaction.setCurrencyPair(pair);
        transaction.setBaseBalance(savedBaseBalance);
        transaction.setQuoteBalance(savedQuoteBalance);
        transaction.setType(type);
        transaction.setPrice(getCurrentPrice(pair, type));
        transaction.setQuantity(transactionPayload.getQuantity());
        transaction.setTimestamp(LocalDateTime.now());

        Transaction savedTransaction = transactionRepository.save(transaction);
        return savedTransaction;
    }

    @Override
    public List<Transaction> getTransactionHistory(String username) {
        return transactionRepository.findByUsername(username);
    }

    @Override
    public List<Transaction> getTransactionHistory(String username, String pair) {
        return transactionRepository.findByUsernameAndPair(username, pair);
    }

    private void addNonExistBalance(User user, CurrencyPair pair) {
        if (user.getBalances().stream().noneMatch(b -> b.getCurrency().equals(pair.getBaseCurrency()))) {
            user.getBalances().add(initEmptyBalance(user, pair.getBaseCurrency()));
        }
        if (user.getBalances().stream().noneMatch(b -> b.getCurrency().equals(pair.getQuoteCurrency()))) {
            user.getBalances().add(initEmptyBalance(user, pair.getQuoteCurrency()));
        }
    }

    private Balance initEmptyBalance(User user, Currency currency) {
        Balance balance = new Balance();
        balance.setUser(user);
        balance.setCurrency(currency);
        balance.setQuantity(BigDecimal.ZERO);
        return balance;
    }

//    private Balance getBaseBalance(User user, CurrencyPair pair, Transaction.Type type) {
//        if (type == Transaction.Type.BUY) {
//            return user.getBalances().stream().filter(b -> b.getCurrency().equals(pair.getBaseCurrency())).findFirst().orElse(null);
//        } else {
//            return user.getBalances().stream().filter(b -> b.getCurrency().equals(pair.getQuoteCurrency())).findFirst().orElse(null);
//        }
//    }

    private Balance getBaseBalance(User user, CurrencyPair pair, Transaction.Type type) {
        return user.getBalances().stream().filter(b -> b.getCurrency().equals(pair.getBaseCurrency())).findFirst().orElse(null);
    }

//    private Balance getQuoteBalance(User user, CurrencyPair pair, Transaction.Type type) {
//        if (type == Transaction.Type.BUY) {
//            return user.getBalances().stream().filter(b -> b.getCurrency().equals(pair.getQuoteCurrency())).findFirst().orElse(null);
//        } else {
//            return user.getBalances().stream().filter(b -> b.getCurrency().equals(pair.getBaseCurrency())).findFirst().orElse(null);
//        }
//    }

    private Balance getQuoteBalance(User user, CurrencyPair pair, Transaction.Type type) {
        return user.getBalances().stream().filter(b -> b.getCurrency().equals(pair.getQuoteCurrency())).findFirst().orElse(null);
    }

    private BigDecimal calculateTotalPrice(CurrencyPair pair, Transaction.Type type, BigDecimal quantity) {
        if (type == Transaction.Type.BUY) {
            return getCurrentPrice(pair, type).multiply(quantity);
        } else {
            return getCurrentPrice(pair, type).multiply(quantity);
        }
    }

    private BigDecimal getCurrentPrice(CurrencyPair pair, Transaction.Type type) {
        if (type == Transaction.Type.BUY) {
            return pair.getAskPrice();
        } else {
            return pair.getBidPrice();
        }
    }
}
