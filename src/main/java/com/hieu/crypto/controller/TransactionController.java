package com.hieu.crypto.controller;

import com.hieu.crypto.entity.CurrencyPair;
import com.hieu.crypto.entity.Transaction;
import com.hieu.crypto.model.TransactionPayload;
import com.hieu.crypto.service.core.CurrencyPairService;
import com.hieu.crypto.service.core.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/{username}/transactions")
    public ResponseEntity<Transaction> doTransaction(@PathVariable String username, @RequestBody TransactionPayload transactionPayload) {
        transactionPayload.setUsername(username);
        return ResponseEntity.ok().body(transactionService.doTransaction(transactionPayload));
    }

    @GetMapping("/{username}/transactions/histories")
    public ResponseEntity<List<Transaction>> getHistories(@PathVariable String username) {
        return ResponseEntity.ok().body(transactionService.getTransactionHistory(username));
    }

    @GetMapping("/{username}/transactions/{pair}/histories")
    public ResponseEntity<List<Transaction>> getHistories(@PathVariable String username, @PathVariable String pair) {
        return ResponseEntity.ok().body(transactionService.getTransactionHistory(username, pair));
    }
}
