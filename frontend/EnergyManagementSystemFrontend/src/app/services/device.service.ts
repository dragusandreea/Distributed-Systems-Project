import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {Device} from "../models/Device";
import jwt_decode from "jwt-decode";
import {Response} from "../models/Response";

@Injectable({
  providedIn: 'root'
})
export class DeviceService {
  baseURL: string = "http://localhost:8081/devices";
  baseSensorURL: string =  "http://localhost:8083/sensors";

  constructor(private httpClient: HttpClient) {
  }

  getDeviceById(id: string): Observable<Device> | undefined {
    let auth_token = localStorage.getItem("tokenLogin")
    let header = new HttpHeaders()
      .set('Content-Type', 'application/json')
      .set('Authorization', `Bearer ${auth_token}`)
    if (this.checkIsAdmin()) {
      return this.httpClient.get<Device>(this.baseURL + '/read/getById/' + id, {headers: header});
    }
    return undefined;
  }

  getAllDevices() {
    let auth_token = localStorage.getItem("tokenLogin")
    let header = new HttpHeaders()
      .set('Content-Type', 'application/json')
      .set('Authorization', `Bearer ${auth_token}`)
    if (this.checkIsAdmin()) {
      return this.httpClient.get<Device[]>(this.baseURL + "/read/getAll", {headers: header});
    }
    return undefined;
  }

  getDeviceByDescription(description: string) {
    let auth_token = localStorage.getItem("tokenLogin")
    let header = new HttpHeaders()
      .set('Content-Type', 'application/json')
      .set('Authorization', `Bearer ${auth_token}`)
    return this.httpClient.get<Device[]>(this.baseURL + "/read/getByDescription/" + description, {headers: header});
  }

  getDeviceByAddress(address: string) {
    let auth_token = localStorage.getItem("tokenLogin")
    let header = new HttpHeaders()
      .set('Content-Type', 'application/json')
      .set('Authorization', `Bearer ${auth_token}`)
    return this.httpClient.get<Device>(this.baseURL + "/read/getByAddress/" + address, {headers: header});
  }

  getDeviceByHourlyLimit(hourlyLimit: number) {
    let auth_token = localStorage.getItem("tokenLogin")
    let header = new HttpHeaders()
      .set('Content-Type', 'application/json')
      .set('Authorization', `Bearer ${auth_token}`)
    return this.httpClient.get<Device>(this.baseURL + "/read/getByHourlyLimit/" + hourlyLimit, {headers: header});
  }

  getDeviceByHourlyLimitLessThan(hourlyLimit: number) {
    let auth_token = localStorage.getItem("tokenLogin")
    let header = new HttpHeaders()
      .set('Content-Type', 'application/json')
      .set('Authorization', `Bearer ${auth_token}`)
    return this.httpClient.get<Device>(this.baseURL + "/read/getByHourlyLimitLessThan/" + hourlyLimit, {headers: header});
  }

  getDeviceByHourlyLimitGreaterThan(hourlyLimit: number) {
    let auth_token = localStorage.getItem("tokenLogin")
    let header = new HttpHeaders()
      .set('Content-Type', 'application/json')
      .set('Authorization', `Bearer ${auth_token}`)
    return this.httpClient.get<Device>(this.baseURL + "/read/getByHourlyLimitGreaterThan/" + hourlyLimit, {headers: header});
  }

  getDeviceByOwnerId(ownerId: string) {
    let auth_token = localStorage.getItem("tokenLogin")
    let header = new HttpHeaders()
      .set('Content-Type', 'application/json')
      .set('Authorization', `Bearer ${auth_token}`)
    if(this.checkIsAdmin() || this.checkOwner(ownerId)) {
      return this.httpClient.get<Device[]>(this.baseURL + "/read/getByOwnerId/" + ownerId, {headers: header});
    } else {
      return undefined;
    }
  }

  addDevice(device: Device): Observable<Device> | string {
    let auth_token = localStorage.getItem("tokenLogin")
    let header = new HttpHeaders()
      .set('Content-Type', 'application/json')
      .set('Authorization', `Bearer ${auth_token}`)
    if (this.checkIsAdmin()) {
        return this.httpClient.post<Device>(this.baseURL + "/create", device, {headers: header});
    } else {
      return "You do not have admin rights";
    }
  }

  addDeviceToSensorList(deviceId: string): Observable<string> {
    let auth_token = localStorage.getItem("tokenLogin")
    let header = new HttpHeaders()
      .set('Content-Type', 'application/json')
      .set('Authorization', `Bearer ${auth_token}`)
    return  this.httpClient.post<string>(this.baseSensorURL + "/add/" + deviceId, {headers: header})
  }

  updateDevice(device: Device): Observable<Device> | string {
    let auth_token = localStorage.getItem("tokenLogin")
    let header = new HttpHeaders()
      .set('Content-Type', 'application/json')
      .set('Authorization', `Bearer ${auth_token}`)
    if (this.checkIsAdmin()) {
      return this.httpClient.put<Device>(this.baseURL + "/update", device, {headers: header});
    }
    return "You do not have admin rights";
  }

  deleteDeviceById(id: string): Observable<Response> | undefined {
    let auth_token = localStorage.getItem("tokenLogin")
    let header = new HttpHeaders()
      .set('Content-Type', 'application/json')
      .set('Authorization', `Bearer ${auth_token}`)
    if (this.checkIsAdmin()) {
      return this.httpClient.delete<Response>(this.baseURL + "/delete/deleteById/" + id, {headers: header});
    }
    return undefined;
  }

  deleteDeviceByOwnerId(ownerId: string): Observable<Response> | undefined {
    let auth_token = localStorage.getItem("tokenLogin")
    let header = new HttpHeaders()
      .set('Content-Type', 'application/json')
      .set('Authorization', `Bearer ${auth_token}`)
    if (this.checkIsAdmin()) {
      return this.httpClient.delete<Response>(this.baseURL + "/delete/deleteByOwnerId/" + ownerId, {headers: header});
    }
    return undefined;
  }

  private checkIsAdmin(): boolean {
    let auth_token = localStorage.getItem("tokenLogin")
    if (auth_token != null) {
      let tokenPayload: any;
      tokenPayload = jwt_decode(auth_token);
      return tokenPayload.userType === "ADMIN";
    }
    return false;
  }

  private checkOwner(ownerId: string): boolean {
    let auth_token = localStorage.getItem("tokenLogin")
    if (auth_token != null) {
      let tokenPayload: any;
      tokenPayload = jwt_decode(auth_token);
      return tokenPayload.id === ownerId;
    }
    return false;
  }


}
