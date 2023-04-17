package com.hieu.crypto.controller;

import com.hieu.crypto.entity.CurrencyPair;
import com.hieu.crypto.service.core.CurrencyPairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/currency-pairs")
public class CurrencyPairController {

    @Autowired
    private CurrencyPairService currencyPairService;

    @GetMapping("/")
    public ResponseEntity<List<CurrencyPair>> getAllCurrencyPairs() {
        return ResponseEntity.ok().body(currencyPairService.findAll());
    }

    @GetMapping("/{symbol}")
    public ResponseEntity<CurrencyPair> getCurrencyPairBySymbol(@PathVariable String symbol) {
        return ResponseEntity.ok().body(currencyPairService.findBySymbol(symbol));
    }
}
