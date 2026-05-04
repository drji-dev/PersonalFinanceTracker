package com.transaction_service.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.transaction_service.dto.request.TransactionRequest;
import com.transaction_service.model.Transaction;
import com.transaction_service.repository.TransactionRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public List<Transaction> getAllTransactions(Long userId) {
        return transactionRepository.findAllByUserId(userId);
    }

    public List<Transaction> getAllTransactionsByCategory(Long userId, String category) {
        return transactionRepository.findAllByUserIdAndCategory(userId, category);
    }

    public BigDecimal totalAmountByCategory(Long userId, String category) {

        BigDecimal total = transactionRepository.getTotalAmountByCategory(userId, category);
        
        return total != null ? total : BigDecimal.ZERO;
    }

    public void createTransaction(TransactionRequest request, Long userId) {
        Transaction transaction = Transaction.builder()
                .category(request.getCategory())
                .amount(request.getAmount())
                .description(request.getDescription())
                .date(LocalDateTime.now())
                .userId(userId)
                .build();
        transactionRepository.save(transaction);
    }

    public void updateTransaction(Long id, TransactionRequest request, Long userId) {
        Transaction transaction = transactionRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new RuntimeException("Not found"));

        if (request.getAmount() != null)
            transaction.setAmount(request.getAmount());
        if (request.getCategory() != null)
            transaction.setCategory(request.getCategory());
        if (request.getDescription() != null)
            transaction.setDescription(request.getDescription());

        transactionRepository.save(transaction);
    }

    public void deleteTransaction(Long id, Long userId) {
        transactionRepository.deleteByIdAndUserId(id, userId);
    }
}
