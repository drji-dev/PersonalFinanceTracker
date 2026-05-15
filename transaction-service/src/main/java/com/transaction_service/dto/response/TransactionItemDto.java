package com.transaction_service.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransactionItemDto {

    private Long id;
    private BigDecimal amount;
    private String description;
    private String category;
    private LocalDateTime date;

}
