import { Component, OnInit } from '@angular/core';
import { StockDataService } from '../service/data/stock-data.service';
import { Router } from '@angular/router';

export class Stock {
  constructor(
    public id: number,
    public name: string,
    public currentPrice: number,
    public lastUpdate: Date
  ){

  }
}

@Component({
  selector: 'app-list-stocks',
  templateUrl: './list-stocks.component.html',
  styleUrls: ['./list-stocks.component.css']
})
export class ListStocksComponent implements OnInit {

  stocks: Stock[]
  message: string

  constructor(
    private stockService:StockDataService,
    private router : Router
  ) { }

  ngOnInit() {
    this.refreshStocks();
  }

  refreshStocks(){
    //console.log('refreshStock');
    this.stockService.retrieveAllStocks().subscribe(
      response => {
        console.log(response);
        this.stocks = response;
      }
    )
  }

  deleteStock(id) {
    console.log(`delete stock ${id}` )
    this.stockService.deleteStock(id).subscribe (
      response => {
        console.log(response);
        this.message = `Delete of Stock ${id} Successful!`;
        this.refreshStocks();
      }
    )
  }

  updateStock(id) {
    console.log(`update ${id}`)
    this.router.navigate(['stocks',id])
  }

  addStock() {
    this.router.navigate(['stocks',-1])
  }

}
