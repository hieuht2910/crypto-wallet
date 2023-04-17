package com.hieu.crypto.service.core;

import com.hieu.crypto.entity.Transaction;
import com.hieu.crypto.model.TransactionPayload;

import java.util.List;

public interface TransactionService {

    Transaction doTransaction(TransactionPayload transactionPayload);

    List<Transaction> getTransactionHistory(String username);

    List<Transaction> getTransactionHistory(String username, String pair);
}
