package com.assignment.stocksmanagementservices.repository;

import com.assignment.stocksmanagementservices.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends JpaRepository<Stock, Integer> {

}
