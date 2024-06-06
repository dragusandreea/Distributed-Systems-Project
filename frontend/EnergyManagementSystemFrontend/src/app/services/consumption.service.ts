import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {Consumption} from "../models/Consumption";

@Injectable({
  providedIn: 'root'
})
export class ConsumptionService {
  baseURL: string = "http://localhost:8084/consumption";
  constructor(private httpClient: HttpClient) {
  }
  getConsumptionByOwnerId(id: string): Observable<Consumption[]> {
    let auth_token = localStorage.getItem("tokenLogin")
    let header = new HttpHeaders()
      .set('Content-Type', 'application/json')
      .set('Authorization', `Bearer ${auth_token}`)
    return this.httpClient.get<Consumption[]>(this.baseURL + '/read/getAllByOwnerId/' + id, {headers: header});
  }
}
