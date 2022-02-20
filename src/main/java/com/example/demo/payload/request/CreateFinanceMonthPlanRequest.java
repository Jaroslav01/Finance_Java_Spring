package com.example.demo.payload.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class CreateFinanceMonthPlanRequest {
//    @NotEmpty(message = "Amount cannot be empty")
    private Long amount;
}
