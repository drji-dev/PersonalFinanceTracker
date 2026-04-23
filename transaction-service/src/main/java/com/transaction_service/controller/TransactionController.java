package com.transaction_service.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.transaction_service.dto.request.TransactionRequest;
import com.transaction_service.dto.request.TransactionRequest.OnCreate;
import com.transaction_service.dto.request.TransactionRequest.OnUpdate;
import com.transaction_service.dto.response.TransactionResponse;
import com.transaction_service.model.Transaction;
import com.transaction_service.service.TransactionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/transaction")
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping
    public ResponseEntity<TransactionResponse> getAllTransactions(@RequestHeader("User-Id") Long userId) {
        List<Transaction> transactions = transactionService.getAllTransactions(userId);
        if (transactions != null) {
            return ResponseEntity.ok(convertToResponse(transactions));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/totalamount")
    public ResponseEntity<BigDecimal> getTotalAmount(@RequestHeader Long userId) {
        return ResponseEntity.ok(transactionService.totalAmount(userId));
    }

    @PostMapping
    public ResponseEntity<Void> createTransaction(
            @Validated(OnCreate.class) @RequestBody TransactionRequest request,
            @RequestHeader("User-Id") Long userId) {

        transactionService.createTransaction(request, userId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateTransaction(
            @Validated(OnUpdate.class) @RequestBody TransactionRequest request,
            @PathVariable Long id, @RequestHeader("User-Id") Long userId) {
        transactionService.updateTransaction(id, request, userId);

        return ResponseEntity.noContent().build();
    }

    private TransactionResponse convertToResponse(List<Transaction> transaction) {

        BigDecimal total = transaction.stream()
                .map(t -> t.getAmount())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        TransactionResponse response = TransactionResponse.builder()
                .transactions(transaction)
                .totalAmount(total)
                .build();

        return response;
    }
}
