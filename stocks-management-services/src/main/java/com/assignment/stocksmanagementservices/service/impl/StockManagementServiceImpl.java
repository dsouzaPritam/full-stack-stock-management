package com.assignment.stocksmanagementservices.service.impl;

import com.assignment.stocksmanagementservices.domain.Stock;
import com.assignment.stocksmanagementservices.exception.model.StockNotFoundException;
import com.assignment.stocksmanagementservices.repository.StockRepository;
import com.assignment.stocksmanagementservices.service.StockManagementService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Handles all the management of the stocks in H2 (in memory database)
 */
@Service
public class StockManagementServiceImpl implements StockManagementService{

    @Autowired
    private StockRepository stockRepository;

    /**
     * Fetch list of all stocks
     * @return
     */
    @Override
    public List<Stock> retrieveAllStocks() {
        return mapEntityListToDomain(stockRepository.findAll());
    }

    /**
     * Fetch stock from memory database
     * @param id
     * @return
     */
    @Override
    public Stock getStock(Integer id) {
        Optional<com.assignment.stocksmanagementservices.entity.Stock> stockEntity = stockRepository.findById(id);
        return stockEntity.isPresent() ? mapEntityToDomainStock(stockEntity.get()) : null;
    }

    /**
     * Create stock in memory database
     * @param price
     * @param stockName
     * @return
     */
    @Override
    public Stock createStock(Integer price, String stockName) {
        com.assignment.stocksmanagementservices.entity.Stock entityStock = new com.assignment.stocksmanagementservices.entity.Stock();
        entityStock.setCurrentPrice(price);
        entityStock.setLastUpdate(new Date());
        entityStock.setName(stockName);
        return mapEntityToDomainStock(stockRepository.save(entityStock));
    }

    /**
     * Update stock in memory database
     * @param id
     * @param updatedPrice
     * @return
     */
    @Override
    public Stock updateStock(Integer id, Integer updatedPrice) {
        com.assignment.stocksmanagementservices.entity.Stock entityStock  = stockRepository.getOne(id);
        if(entityStock != null){
            entityStock.setCurrentPrice(updatedPrice);
            entityStock.setLastUpdate(new Date());
        }
        return mapEntityToDomainStock(stockRepository.save(entityStock));
    }

    /**
     * Delete stock in memory database
     * @param id
     */
    @Override
    public void deleteStock(Integer id){
        Optional<com.assignment.stocksmanagementservices.entity.Stock> stockEntity = stockRepository.findById(id);
        if(!stockEntity.isPresent()){
            throw new StockNotFoundException("Stock could not be deleted , as not found for id : " + id );
        }
        // else delete
        stockRepository.deleteById(id);
    }

    /**
     * Maps list of entity object to domain
     * @param entityStocksList
     * @return
     */
    private List<Stock> mapEntityListToDomain(List<com.assignment.stocksmanagementservices.entity.Stock> entityStocksList){
        List<Stock> stocks = new ArrayList<>();
        if(entityStocksList != null && !entityStocksList.isEmpty()) {
            for (com.assignment.stocksmanagementservices.entity.Stock entityStock : entityStocksList) {
                stocks.add(mapEntityToDomainStock(entityStock));
            }
        }
        return stocks;
    }

    /**
     * Maps entity bean to domain object
     * @param entityStock
     * @return
     */
    private Stock mapEntityToDomainStock(final com.assignment.stocksmanagementservices.entity.Stock entityStock){
        Stock domainStock = new Stock();
        BeanUtils.copyProperties(entityStock, domainStock);
        return domainStock;
    }

}
