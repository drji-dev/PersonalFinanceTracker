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

    public BigDecimal totalAmount(Long userId) {
        return transactionRepository.getTotalAmount(userId);
    }

    public void createTransaction(TransactionRequest request, Long userId) {
        Transaction transaction = Transaction.builder()
                .category(request.getCategory())
                .amount(request.getAmount())
                .description(request.getDescription())
                .date(LocalDateTime.now())
                .build();
        transactionRepository.save(transaction);
    }

    public void updateTransaction(Long id, TransactionRequest request, Long userId) {
        Transaction transaction = transactionRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new RuntimeException("Not found"));

        transaction.setAmount(request.getAmount());
        transaction.setCategory(request.getCategory());
        transaction.setDescription(request.getDescription());
        transactionRepository.save(transaction);
    }
}
