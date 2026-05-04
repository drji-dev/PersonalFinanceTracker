package com.transaction_service.dto.response;

import java.math.BigDecimal;
import java.util.List;

import com.transaction_service.model.Transaction;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransactionResponse {

    private List<Transaction> transactions;
    private BigDecimal totalAmount;
}
