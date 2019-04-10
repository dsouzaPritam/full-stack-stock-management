package com.assignment.stocksmanagementservices.service.impl;

import com.assignment.stocksmanagementservices.domain.Stock;
import com.assignment.stocksmanagementservices.repository.StockRepository;
import com.assignment.stocksmanagementservices.service.StockManagementService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class StockManagementServiceImplTest {

    @Mock
    private StockRepository stockRepository;

    @InjectMocks
    private StockManagementService stockManagementService = new StockManagementServiceImpl();

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void test_retrieveAllStocks(){
        // mock
        Mockito
                .when(stockRepository.findAll())
                .thenReturn(mockEntityStockList());
        // execute
        List<Stock> stocks = stockManagementService.retrieveAllStocks();
        // assert/verify
        Mockito.verify(stockRepository).findAll();
        Assert.assertEquals(3, stocks.size());
        Assert.assertEquals(Integer.valueOf(1001), stocks.get(0).getId());
        Assert.assertEquals(Integer.valueOf(1002), stocks.get(1).getId());
        Assert.assertEquals(Integer.valueOf(1003), stocks.get(2).getId());
    }

    @Test
    public void test_getStock(){
        // mock
        Mockito
                .when(stockRepository.findById(Mockito.anyInt()))
                .thenReturn(java.util.Optional.ofNullable(createEntityMockStock(1001, "company-1", 200)));
        // execute
        Stock stock = stockManagementService.getStock(Mockito.anyInt());
        // assert/verify
        Mockito.verify(stockRepository).findById(Mockito.anyInt());
        Assert.assertEquals(Integer.valueOf(1001), stock.getId());
        Assert.assertEquals("company-1", stock.getName());
        Assert.assertEquals(Integer.valueOf(200), stock.getCurrentPrice());
    }


    @Test
    public void test_createStock(){
        // mock
        Mockito
                .when(stockRepository.save(Mockito.any(com.assignment.stocksmanagementservices.entity.Stock.class)))
                .thenReturn(createEntityMockStock(1001, "test-company", 200));
        // execute
        Stock stock = stockManagementService.createStock(200, "test-company");
        // assert/verify
        Mockito.verify(stockRepository).save(Mockito.any(com.assignment.stocksmanagementservices.entity.Stock.class));
        Assert.assertEquals(Integer.valueOf(1001), stock.getId());
        Assert.assertEquals("test-company", stock.getName());
        Assert.assertEquals(Integer.valueOf(200), stock.getCurrentPrice());
    }


    @Test
    public void test_updateStock(){
        // mock
        Mockito
                .when(stockRepository.getOne(Mockito.anyInt()))
                .thenReturn(createEntityMockStock(1001, "test-company", 200));
        Mockito
                .when(stockRepository.save(Mockito.any(com.assignment.stocksmanagementservices.entity.Stock.class)))
                .thenReturn(createEntityMockStock(1001, "test-company", 250));

        // execute
        Stock stock = stockManagementService.updateStock(1001, 250);
        // assert/verify
        Mockito.verify(stockRepository).getOne(Mockito.anyInt());
        Mockito.verify(stockRepository).save(Mockito.any(com.assignment.stocksmanagementservices.entity.Stock.class));
        Assert.assertEquals(Integer.valueOf(1001), stock.getId());
        Assert.assertEquals("test-company", stock.getName());
        Assert.assertEquals(Integer.valueOf(250), stock.getCurrentPrice());
    }

    private List<com.assignment.stocksmanagementservices.entity.Stock> mockEntityStockList(){
        List<com.assignment.stocksmanagementservices.entity.Stock> stocks = new ArrayList<>();
        stocks.add(createEntityMockStock(1001, "company-1", 200));
        stocks.add(createEntityMockStock(1002, "company-2", 400));
        stocks.add(createEntityMockStock(1003, "company-3", 100));
        return stocks;
    }

    private com.assignment.stocksmanagementservices.entity.Stock createEntityMockStock(final Integer id, String name, Integer stockPrice){
        com.assignment.stocksmanagementservices.entity.Stock entityStock = new com.assignment.stocksmanagementservices.entity.Stock();
        entityStock.setId(id);
        entityStock.setName(name);
        entityStock.setCurrentPrice(stockPrice);
        entityStock.setLastUpdate(new Date());
        return entityStock;
    }

}
