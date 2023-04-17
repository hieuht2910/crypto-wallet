package com.hieu.crypto.repository;

import com.hieu.crypto.entity.Currency;
import com.hieu.crypto.entity.CurrencyPair;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyPairRepository extends JpaRepository<CurrencyPair, Long> {

    CurrencyPair findBySymbol(String symbol);
}
