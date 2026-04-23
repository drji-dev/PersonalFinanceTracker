package com.transaction_service.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.transaction_service.model.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.userId = :userId")
    BigDecimal getTotalAmount(@Param("userId") Long userId);

    Optional<Transaction> findByIdAndUserId(Long id, Long userId);

    @Query("SELECT t FROM Transaction t WHERE t.userId = :userId ORDER BY t.date DESC")
    List<Transaction> findAllByUserId(@Param("userId") Long userId);

    void deleteByIdAndUserId(Long id, Long userId);

    @Query("SELECT t FROM Transaction t WHERE t.category = :category AND t.userId = :userId ORDER BY t.date DESC")
    List<Transaction> findByCategory(@Param("category") String category, @Param("userId") Long userId);
}
