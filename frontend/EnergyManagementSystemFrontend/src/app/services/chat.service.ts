import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {User} from "../models/User";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {ChatMessage} from "../models/ChatMessage";

@Injectable({
  providedIn: 'root'
})
export class ChatService {
  baseURL: string = "http://localhost:8085";

  constructor(private httpClient: HttpClient) { }

  getMessagesBySenderUidAndReceiverUid(senderUid: string, receiverUid:string): Observable<ChatMessage[]> | any {
    let auth_token = localStorage.getItem("tokenLogin")
    let header = new HttpHeaders()
      .set('Content-Type', 'application/json')
      .set('Authorization', `Bearer ${auth_token}`)
    return this.httpClient.get<ChatMessage[]>(this.baseURL + "/read/getAllBySenderIdAndReceiverId/" + senderUid + "/" + receiverUid, {headers: header});
  }

  getMessagesBetweenSenderUidAndReceiverUid(senderUid: string, receiverUid:string): Observable<ChatMessage[]> | any {
    let auth_token = localStorage.getItem("tokenLogin")
    let header = new HttpHeaders()
      .set('Content-Type', 'application/json')
      .set('Authorization', `Bearer ${auth_token}`)
    return this.httpClient.get<ChatMessage[]>(this.baseURL + "/read/getAllBetweenSenderIdAndReceiverId/" + senderUid + "/" + receiverUid, {headers: header});
  }

  updateSeen(chatMessage: ChatMessage) {
    chatMessage.seen = true
    let auth_token = localStorage.getItem("tokenLogin")
    let header = new HttpHeaders()
      .set('Content-Type', 'application/json')
      .set('Authorization', `Bearer ${auth_token}`)
    return this.httpClient.put<ChatMessage>(this.baseURL + "/update", chatMessage, {headers: header});

  }
}
