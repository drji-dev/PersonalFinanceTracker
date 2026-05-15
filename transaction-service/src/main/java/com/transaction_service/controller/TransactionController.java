package com.transaction_service.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.transaction_service.dto.request.TransactionRequest;
import com.transaction_service.dto.request.TransactionRequest.OnCreate;
import com.transaction_service.dto.request.TransactionRequest.OnUpdate;
import com.transaction_service.dto.response.TransactionItemDto;
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
    public ResponseEntity<TransactionResponse> getAllTransactions(@AuthenticationPrincipal Jwt jwt) {

        List<Transaction> transactions = transactionService.getAllTransactions(userId(jwt));
        return ResponseEntity.ok(convertToResponse(transactions));
    }

    @GetMapping("/category")
    public ResponseEntity<TransactionResponse> getAllTransactionByCategory(
            @RequestParam(required = false) String category,
            @AuthenticationPrincipal Jwt jwt) {

        List<Transaction> transactions = transactionService.getAllTransactionsByCategory(userId(jwt), category);
        if (transactions != null) {
            return ResponseEntity.ok(convertToResponse(transactions));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/category/stats")
    public ResponseEntity<BigDecimal> getTotalAmountByCategory(@RequestParam(required = false) String category,
            @AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.ok(transactionService.totalAmountByCategory(userId(jwt), category));
    }

    @PostMapping
    public ResponseEntity<Void> createTransaction(
            @Validated(OnCreate.class) @RequestBody TransactionRequest request,
            @AuthenticationPrincipal Jwt jwt) {

        transactionService.createTransaction(request, userId(jwt));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateTransaction(
            @Validated(OnUpdate.class) @RequestBody TransactionRequest request,
            @PathVariable Long id, @AuthenticationPrincipal Jwt jwt) {
        transactionService.updateTransaction(id, request, userId(jwt));

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id, @AuthenticationPrincipal Jwt jwt) {
        transactionService.deleteTransaction(id, userId(jwt));
        return ResponseEntity.noContent().build();
    }

    private TransactionResponse convertToResponse(List<Transaction> transaction) {

        BigDecimal total = transaction.stream()
                .map(t -> t.getAmount())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        List<TransactionItemDto> dtos = transaction.stream().map(t -> TransactionItemDto.builder()
                .id(t.getId())
                .amount(t.getAmount())
                .category(t.getCategory())
                .description(t.getDescription())
                .date(t.getDate())
                .build())
                .toList();

        TransactionResponse response = TransactionResponse.builder()
                .transactions(dtos)
                .totalAmount(total)
                .build();

        return response;
    }

    private Long userId(Jwt jwt) {
        return jwt.getClaim("userId");
    }
}
