package com.assignment.stocksmanagementservices.service;

import com.assignment.stocksmanagementservices.domain.Stock;

import java.util.List;

/**
 * Interface defines the stock management
 */
public interface StockManagementService {

    public List<Stock> retrieveAllStocks();

    public Stock getStock(Integer id);

    public Stock createStock(Integer price, String name);

    public Stock updateStock(Integer id, Integer updatedPrice);


}
