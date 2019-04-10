package com.assignment.stocksmanagementservices.domain;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
public class Stock {

    private Integer id;

    @NotNull
    @Size(min=5, message="Name should have atleast 5 characters")
    private String name;

    @NotNull
    private Integer currentPrice;

    private Date lastUpdate;

}
