import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { API_URL } from 'src/app/app.constants';
import { Stock } from 'src/app/list-stocks/list-stocks.component';

@Injectable({
  providedIn: 'root'
})
export class StockDataService {

  constructor(
    private http:HttpClient
  ) { }

  retrieveAllStocks() {
    return this.http.get<Stock[]>(`${API_URL}/api/stocks`);
  }

  deleteStock(id){
    return this.http.delete(`${API_URL}/api/stocks/${id}`);
  }

  retrieveStock(id){
    return this.http.get<Stock>(`${API_URL}/api/stocks/${id}`);
  }

  updateStock(id, stock){
    return this.http.put(`${API_URL}/api/stocks/${id}`, stock);
  }

  createStock(stock){
    return this.http.post(`${API_URL}/api/stocks`, stock);
  }
}
