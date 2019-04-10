package com.assignment.stocksmanagementservices.domain;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class StockDetail {

    @NotNull
    private Integer currentPrice;
}
