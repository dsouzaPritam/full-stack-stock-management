package com.assignment.stocksmanagementservices.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@AllArgsConstructor
@Data
@Entity
@NoArgsConstructor
public class Stock {

    @Id
    @GeneratedValue
    private Integer id;

    private String name;

    private Integer currentPrice;

    private Date lastUpdate;

}
