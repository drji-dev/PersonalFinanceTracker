package com.transaction_service.dto.response;

import java.math.BigDecimal;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransactionResponse {

    private List<TransactionItemDto> transactions;
    private BigDecimal totalAmount;
}
