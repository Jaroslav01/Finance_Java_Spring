package com.example.demo.payload.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class CreateFinanceRecordRequest {

//    @NotEmpty(message = "Amount cannot be empty")
    private Long amount;

//    @NotEmpty(message = "Type cannot be empty")
    private Integer type;
}
