package com.hieu.crypto.repository;

import com.hieu.crypto.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("SELECT t FROM Transaction t JOIN t.user u ON u.username = :username")
    List<Transaction> findByUsername(@Param("username") String username);

    @Query("SELECT t FROM Transaction t JOIN t.user u ON u.username = :username JOIN t.currencyPair cp ON cp.symbol = :symbol")
    List<Transaction> findByUsernameAndPair(@Param("username") String username, @Param("symbol") String symbol);
}
