package com.transaction_service.dto.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class TransactionRequest {

    public interface OnCreate {
    } // Маркер для создания

    public interface OnUpdate {
    } // Маркер для обновления

    @NotNull(groups = OnCreate.class)
    @Positive
    private BigDecimal amount;

    private String description;
    private String category;

}
