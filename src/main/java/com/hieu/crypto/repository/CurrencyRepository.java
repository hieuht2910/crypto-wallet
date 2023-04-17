package com.hieu.crypto.repository;

import com.hieu.crypto.entity.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Long> {

    Currency findBySymbol(String symbol);
}
