import {Component, OnInit} from '@angular/core';
import jwt_decode from "jwt-decode";
import * as SockJS from "sockjs-client";
import {Notification} from "../../../models/Notification";
import * as Stomp from 'stompjs';

import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-admin-home-page',
  templateUrl: './admin-home-page.component.html',
  styleUrls: ['./admin-home-page.component.css']
})
export class AdminHomePageComponent implements OnInit{
  ownerId: string = ""
  constructor(private _snackBar: MatSnackBar) {
  }
  ngOnInit(): void {
    this.connectNotifications()
  }
 logOut() {
   localStorage.clear()
 }

  connectNotifications() {
    let token = localStorage.getItem("tokenLogin")
    let tokenPayload: any;
    if (token != null) {
      tokenPayload = jwt_decode(token);
      this.ownerId = tokenPayload.id
    }
    var socket = new SockJS('http://localhost:8085/private-notification-typing');
    var stompClient: Stomp.Client = Stomp.over(socket);
    stompClient.connect({}, (frame) => {
      console.log('Connected: ' + frame);
      stompClient.subscribe('/notification-typing/'+this.ownerId, (message: {body: string}): any => {
        let notif: Notification = JSON.parse(message.body)
        this._snackBar.open(notif.message, "close");

      });
    });
  }

}
