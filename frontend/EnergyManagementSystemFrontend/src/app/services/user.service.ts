import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {User} from "../models/User";
import jwt_decode from "jwt-decode";
import {DeviceService} from "./device.service";
import {Response} from "../models/Response";

@Injectable({
  providedIn: 'root'
})
export class UserService {
  baseURL: string = "http://localhost:8082/users";

  constructor(private httpClient: HttpClient, private deviceService: DeviceService) {
  }

  login(username: any, password: any): Observable<string> {
    let header = new HttpHeaders()
      .set('Content-Type', 'application/json')
    const body: string = JSON.stringify({
      username: username,
      password: password
    });
    return this.httpClient.post<string>(this.baseURL + '/login', body, {headers: header});
  }

  register(user: User): Observable<string> {
    let header = new HttpHeaders()
      .set('Content-Type', 'application/json')
    return this.httpClient.post<string>(this.baseURL + "/register", user, {headers: header});
  }

  getUserById(id: string): Observable<User> | any {
    let auth_token = localStorage.getItem("tokenLogin")
    let header = new HttpHeaders()
      .set('Content-Type', 'application/json')
      .set('Authorization', `Bearer ${auth_token}`)
    if (this.checkIsAdmin()) {
      return this.httpClient.get<User>(this.baseURL + '/read/getById/' + id, {headers: header});
    } else {
      alert("You do not have admin rights");
    }
  }

  getAllUsers(): Observable<User[]> | any {
    let auth_token = localStorage.getItem("tokenLogin")
    let header = new HttpHeaders()
      .set('Content-Type', 'application/json')
      .set('Authorization', `Bearer ${auth_token}`)
    if (this.checkIsAdmin()) {
      return this.httpClient.get<User[]>(this.baseURL + "/read/getAll", {headers: header});
    } else {
      alert("You do not have admin rights");
    }
  }

  getUserByName(name: string): Observable<User[]> | any {
    let auth_token = localStorage.getItem("tokenLogin")
    let header = new HttpHeaders()
      .set('Content-Type', 'application/json')
      .set('Authorization', `Bearer ${auth_token}`)
    if (this.checkIsAdmin()) {
      return this.httpClient.get<User[]>(this.baseURL + "/read/getByName/" + name, {headers: header});
    } else {
      alert("You do not have admin rights");
    }
  }

  getUsersByUserType(userType: string): Observable<User[]> | any {
    let auth_token = localStorage.getItem("tokenLogin")
    let header = new HttpHeaders()
      .set('Content-Type', 'application/json')
      .set('Authorization', `Bearer ${auth_token}`)
      return this.httpClient.get<User[]>(this.baseURL + "/read/getByUserType/" + userType, {headers: header});
  }

  getUserByUsername(username: string): Observable<User[]> | any {
    let auth_token = localStorage.getItem("tokenLogin")
    let header = new HttpHeaders()
      .set('Content-Type', 'application/json')
      .set('Authorization', `Bearer ${auth_token}`)
    if (this.checkIsAdmin()) {
      return this.httpClient.get<User>(this.baseURL + "/read/getByUsername/" + username, {headers: header});
    } else {
      alert("You do not have admin rights");
    }
  }

  addUser(user: User): Observable<User> | any {
    let auth_token = localStorage.getItem("tokenLogin")
    let header = new HttpHeaders()
      .set('Content-Type', 'application/json')
      .set('Authorization', `Bearer ${auth_token}`)
    if(this.checkIsAdmin()) {
      return this.httpClient.post<User>(this.baseURL + "/create", user, {headers: header});
    } else {
      alert("You do not have admin rights");
    }
  }

  updateUser(user: User): Observable<User> | any {
    let auth_token = localStorage.getItem("tokenLogin")
    let header = new HttpHeaders()
      .set('Content-Type', 'application/json')
      .set('Authorization', `Bearer ${auth_token}`)
    if(this.checkIsAdmin()) {
      return this.httpClient.put<User>(this.baseURL + "/update", user, {headers: header});
    } else {
      alert("You do not have admin rights");
    }
  }

  deleteUser(id: string): Observable<User> | any {
    let auth_token = localStorage.getItem("tokenLogin")
    let header = new HttpHeaders()
      .set('Content-Type', 'application/json')
      .set('Authorization', `Bearer ${auth_token}`)
    if(this.checkIsAdmin()) {
      this.deviceService.deleteDeviceByOwnerId(id)?.subscribe((response: Response) => {
        alert(response.message)
      })
      return this.httpClient.delete<User>(this.baseURL + "/delete/deleteById/" + id, {headers: header});
    } else {
      alert("You do not have admin rights");
    }
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


}
