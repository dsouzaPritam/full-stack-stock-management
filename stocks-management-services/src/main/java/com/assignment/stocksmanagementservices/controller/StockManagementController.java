package com.assignment.stocksmanagementservices.controller;

import com.assignment.stocksmanagementservices.domain.Stock;
import com.assignment.stocksmanagementservices.domain.StockDetail;
import com.assignment.stocksmanagementservices.exception.model.StockNotFoundException;
import com.assignment.stocksmanagementservices.service.StockManagementService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@CrossOrigin(origins="http://localhost:4200")
@Slf4j
@RestController
@RequestMapping("/api")
@Api(value = "StockManagement", description = "REST API which enables to get list of stocks/ create/ update etc. ", tags = { "StockManagement" })
public class StockManagementController {

    @Autowired
    private StockManagementService stockManagementService;

    /**
     * Get the complete list of stocks
     * @return
     */
    @ApiOperation(value="Get List of Stocks", tags = { "StockManagement" })
    @GetMapping(value="/stocks", produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> stockList() {
        log.debug(" Fetch list of all stocks ");
        List<Stock> stocks = stockManagementService.retrieveAllStocks();
        return new ResponseEntity(stocks , HttpStatus.OK);
    }


    /**
     * Get one stock from the list
     * @param id
     * @return
     */
    @ApiOperation(value="Get particular Stock", tags = { "StockManagement" })
    @GetMapping(value = "/stocks/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Resource<Stock> getStockById(@PathVariable int id) {
        log.debug("Fetch a stock with id {} ", id);
        Stock stock = stockManagementService.getStock(id);
        if (stock == null ) {
            throw new StockNotFoundException("Stock with id : " + id + " not found.");
        }

        //HATEOAS "all-stocks"
        Resource<Stock> resource = new Resource<Stock>(stock);
        ControllerLinkBuilder linkTo =
                linkTo(methodOn(this.getClass()).stockList());
        resource.add(linkTo.withRel("all-stocks"));

        return resource;
    }

    /**
     * Create a stock
     * @param stock
     * @return
     */
    @ApiOperation(value="Create stock", tags = { "StockManagement" })
    @PostMapping(value = "/stocks", consumes = MediaType.APPLICATION_JSON_VALUE, produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> createStock(@Valid @RequestBody Stock stock) {
        log.debug("Create a new stock => name {} , currentPrice {}");
        Stock stockCreated = stockManagementService.createStock(stock.getCurrentPrice(), stock.getName() );
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(stockCreated.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    /**
     * Update stock price
     * @param stockId
     * @param stockDetailForUpdate
     * @return
     */
    @ApiOperation(value="Update stock", tags = { "StockManagement" })
    @PutMapping(value = "/stocks/{stockId:\\d{1,4}}", consumes=MediaType.APPLICATION_JSON_VALUE ,produces=MediaType.APPLICATION_JSON_VALUE )
    public Resource<Stock> updateStock(@PathVariable int stockId, @Valid @RequestBody StockDetail stockDetailForUpdate){
        log.debug(" Update stock {}  -> price {} ", stockId, stockDetailForUpdate.getCurrentPrice());
        // update stock in database (in memory)
        Stock updatedStock = stockManagementService.updateStock(stockId, stockDetailForUpdate.getCurrentPrice());

        // return the details of the updated stock [HATEOAS "all-stocks"]
        Resource<Stock> resource = new Resource<Stock>(updatedStock);
        ControllerLinkBuilder linkTo =
                linkTo(methodOn(this.getClass()).stockList());
        resource.add(linkTo.withRel("all-stocks"));

        return resource;
    }

    @ApiOperation(value="Delete particular Stock", tags = { "StockManagement" })
    @DeleteMapping(value = "/stocks/{id}")
    public ResponseEntity<Void> deleteStockById(@PathVariable int id) {
        //delete stock in database
        stockManagementService.deleteStock(id);
        // delete has no response body
        return ResponseEntity.noContent().build();
    }

}
