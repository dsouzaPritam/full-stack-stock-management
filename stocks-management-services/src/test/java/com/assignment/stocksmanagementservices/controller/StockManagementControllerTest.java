package com.assignment.stocksmanagementservices.controller;

import com.assignment.stocksmanagementservices.domain.Stock;
import com.assignment.stocksmanagementservices.service.StockManagementService;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(StockManagementController.class)
public class StockManagementControllerTest{

    @MockBean
    private StockManagementService stockManagementService;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void shouldCreateStock() throws Exception{
        // Mocking service
        Mockito
                .when(stockManagementService.createStock(Mockito.anyInt(), Mockito.anyString()))
                .thenReturn(createMockStock(1, "company-test-stock", 463));

        String requestBody = "{\"name\": \"company-new\",   \"currentPrice\": 77 }";
        mockMvc.perform(post("/api/stocks")
                            .contentType(MediaType.APPLICATION_JSON)
                            .header(HttpHeaders.AUTHORIZATION, "Basic YWRtaW5Ccm9rZXI6cGFzc3dvcmQ=")
                            .content(requestBody))
                .andExpect(status().isCreated());


        Mockito.verify(stockManagementService).createStock(Mockito.anyInt(), Mockito.anyString());
    }

    @Test
    public void shouldReturnValidationError_CreateStock_IncorrectName() throws Exception{
        String requestBody = "{\"name\": \"comp\",   \"currentPrice\": 77 }";
        mockMvc.perform(post("/api/stocks")
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Basic YWRtaW5Ccm9rZXI6cGFzc3dvcmQ=")
                .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.description", Matchers.containsString("Name should have atleast 5 characters")));
    }

    @Test
    public void shouldReturnStockList()throws Exception{
        Mockito
                .when(stockManagementService.retrieveAllStocks())
                .thenReturn(mockStockList());

        mockMvc.perform(
                get("/api/stocks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Basic YWRtaW5Ccm9rZXI6cGFzc3dvcmQ="))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1001)))
                .andExpect(jsonPath("$[0].name", is("company-1")))
                .andExpect(jsonPath("$[0].currentPrice", Matchers.is(200)))
                .andExpect(jsonPath("$[1].id", is(1002)))
                .andExpect(jsonPath("$[1].name", is("company-2")))
                .andExpect(jsonPath("$[1].currentPrice", Matchers.is(400)))
                .andExpect(jsonPath("$[2].id", is(1003)))
                .andExpect(jsonPath("$[2].name", is("company-3")))
                .andExpect(jsonPath("$[2].currentPrice", Matchers.is(100)));

        Mockito.verify(stockManagementService).retrieveAllStocks();
    }


    @Test
    public void shouldReturnAStock()throws Exception{
        Mockito
                .when(stockManagementService.getStock(Mockito.anyInt()))
                .thenReturn(createMockStock(1001, "company-2", 400));

        mockMvc.perform(
                get("/api/stocks/1001")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Basic YWRtaW5Ccm9rZXI6cGFzc3dvcmQ="))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1001)))
                .andExpect(jsonPath("$.name", is("company-2")))
                .andExpect(jsonPath("$.currentPrice", Matchers.is(400)));

        Mockito.verify(stockManagementService).getStock(Mockito.anyInt());
    }


    @Test
    public void shouldUpdateStock()throws Exception{
        Mockito
                .when(stockManagementService.updateStock(Mockito.anyInt(), Mockito.anyInt()))
                .thenReturn(createMockStock(1003, "company-3", 77));
        String requestBody = "{\"currentPrice\": 77 }";
        mockMvc.perform(
                put("/api/stocks/1003").contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                        .header(HttpHeaders.AUTHORIZATION, "Basic YWRtaW5Ccm9rZXI6cGFzc3dvcmQ="))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1003)))
                .andExpect(jsonPath("$.name", is("company-3")))
                .andExpect(jsonPath("$.currentPrice", Matchers.is(77)));

        Mockito.verify(stockManagementService).updateStock(Mockito.anyInt(), Mockito.anyInt());

    }

    @Test
    public void shouldReturnValidationError_UpdateStock_IncorrectStockPrice() throws Exception{
        String requestBody = "{\"currentPrice\": null }";
        mockMvc.perform(
                put("/api/stocks/1003").contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                        .header(HttpHeaders.AUTHORIZATION, "Basic YWRtaW5Ccm9rZXI6cGFzc3dvcmQ="))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.description", Matchers.containsString("default message [currentPrice]]; default message [must not be null]")));
    }


    @Test
    public void shouldDeleteAStock()throws Exception{

        mockMvc.perform(
                delete("/api/stocks/1001")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Basic YWRtaW5Ccm9rZXI6cGFzc3dvcmQ="))
                .andExpect(status().isNoContent());

        Mockito.verify(stockManagementService).deleteStock(Mockito.anyInt());
    }

    private List<Stock> mockStockList(){
        List<Stock> stocks = new ArrayList<>();
        stocks.add(createMockStock(1001, "company-1", 200));
        stocks.add(createMockStock(1002, "company-2", 400));
        stocks.add(createMockStock(1003, "company-3", 100));
        return stocks;
    }

    private Stock createMockStock(final Integer id, String name, Integer stockPrice){
        Stock stock = new Stock();
        stock.setId(id);
        stock.setName(name);
        stock.setCurrentPrice(stockPrice);
        stock.setLastUpdate(new Date());
        return stock;
    }


}
