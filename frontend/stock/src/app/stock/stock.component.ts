import { Component, OnInit } from '@angular/core';
import { StockDataService } from '../service/data/stock-data.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Stock } from '../list-stocks/list-stocks.component';

@Component({
  selector: 'app-stock',
  templateUrl: './stock.component.html',
  styleUrls: ['./stock.component.css']
})
export class StockComponent implements OnInit {

  id:number
  stock: Stock

  constructor(
    private stockService: StockDataService,
    private route: ActivatedRoute,
    private router: Router
  ) { }

  ngOnInit() {
    this.id = this.route.snapshot.params['id'];
    // set default values
    this.stock = new Stock(this.id,'', 0 ,new Date());
    
    if(this.id!=-1) {
      this.stockService.retrieveStock(this.id)
          .subscribe (
            data => this.stock = data
          )
    }
  }


  saveStock() {
    if(this.id == -1) { 
      this.stockService.createStock(this.stock)
          .subscribe (
            data => {
              console.log(data)
              this.router.navigate(['stocks'])
            }
          )
    } else {
      this.stockService.updateStock(this.id, this.stock)
          .subscribe (
            data => {
              console.log(data)
              this.router.navigate(['stocks'])
            }
          )
    }
  }

}
