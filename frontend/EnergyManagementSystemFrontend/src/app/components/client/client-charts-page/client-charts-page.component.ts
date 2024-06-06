import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {ConsumptionService} from "../../../services/consumption.service";
import jwt_decode from "jwt-decode";
import {Consumption} from "../../../models/Consumption";
import * as SockJS from "sockjs-client";
import * as Stomp from "stompjs";
import {MatSnackBar} from "@angular/material/snack-bar";
import {Notification} from "../../../models/Notification";
import {DeviceService} from "../../../services/device.service";
import {Device} from "../../../models/Device";
@Component({
  selector: 'app-client-charts-page',
  templateUrl: './client-charts-page.component.html',
  styleUrls: ['./client-charts-page.component.css']
})
export class ClientChartsPageComponent implements OnInit{
  constructor(public consumptionService: ConsumptionService,public deviceService: DeviceService, private _snackBar: MatSnackBar, private cdr: ChangeDetectorRef) {
  }
  selectedDate: string = ""
  selectedDevice: string = ""
  ownerId: any
  hours:number[] = [0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23]
  sumPerHour:number[] = [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
  myDataPoints:{"label": number, "y":number}[] = []
  consumptions: Consumption[] = []
  devices: any = []
  connected: boolean = false
  chartOptions = {
    title: {
      text: "Energy Consumption " + this.selectedDate
    },
    theme: "light2",
    animationEnabled: false,
    exportEnabled: true,
    axisY: {
      includeZero: true,
      valueFormatString: "#####"
    },
    data: [{
      type: "area", //change type to bar, line, area, pie, etc
      yValueFormatString: "#####",
      color: "#ff7a30",
      dataPoints: this.myDataPoints
    }]
  }

  ngOnInit(): void {
    this.connect()
    let token = localStorage.getItem("tokenLogin")
    let tokenPayload: any;
    if (token != null) {
      tokenPayload = jwt_decode(token);
      this.ownerId = tokenPayload.id
      console.log(this.ownerId)
    }

    this.deviceService.getDeviceByOwnerId(this.ownerId)?.subscribe((devices: Device[]) => {
      for(let i = 0; i < devices.length; i++) {
        this.devices[i] = devices[i].description
      }
    })

    this.consumptionService.getConsumptionByOwnerId(this.ownerId).subscribe((consumptions: Consumption[]) => {
      this.consumptions = consumptions;
      console.log("CONSUMPTIONS")
      console.log(consumptions)
    })

  }

  selectDate(){
    let d = (new Date(this.selectedDate))
    this.selectedDate = (new Date(d.getFullYear(), d.getMonth(), d.getDate(), d.getHours(), d.getMinutes() - d.getTimezoneOffset()).toISOString()).toString().substring(0,10)
    let myDataPoints2: { label: number, y: number }[] = [];
    if(this.selectedDate != "" && this.selectedDevice != "") {
      for(let j = 0; j < this.hours.length; j++) {
        myDataPoints2[j] = {"label": this.hours[j], "y": 0}
      }
      for(let i = 0; i < this.consumptions.length; i++) {
        if(this.consumptions[i].deviceDto.description == this.selectedDevice) {
          let d = new Date(this.consumptions[i].timestamp)
          let consumptionDate = (new Date(d.getFullYear(), d.getMonth(), d.getDate(), d.getHours(), d.getMinutes() - d.getTimezoneOffset()).toISOString()).toString().substring(0,10)
          if(this.selectedDate == consumptionDate) {
            myDataPoints2[d.getHours()] = {"label": d.getHours(), "y":this.consumptions[i].hourlyConsumption}
          }
        }
      }
      let myDataPointsNew: { label: number, y: number }[] = [];
      this.myDataPoints = myDataPointsNew.concat(myDataPoints2)
      console.log("Din date: "+ this.selectedDate + " "+ this.selectedDevice)
      let newChart = {
        title: {
          text: "Energy Consumption " + this.selectedDate + " " + this.selectedDevice
        },
        theme: "light2",
        animationEnabled: true,
        exportEnabled: true,
        axisY: {
          includeZero: true,
          valueFormatString: "#####"
        },
        data: [{
          type: "area", //change type to bar, line, area, pie, etc
          yValueFormatString: "#####",
          color: "#ff7a30",
          dataPoints: this.myDataPoints
        }]
      }
      this.chartOptions = newChart
      this.cdr.detectChanges()
    }
  }
  selectDevice(device: string) {
    this.selectedDevice = device;
    let myDataPoints2: { label: number, y: number }[] = [];
    if(this.selectedDate != "" && this.selectedDevice != "") {
      for(let j = 0; j < this.hours.length; j++) {
        myDataPoints2[j] = {"label": this.hours[j], "y": 0}
      }
      for(let i = 0; i < this.consumptions.length; i++) {
        if(this.consumptions[i].deviceDto.description == this.selectedDevice) {
          let d = new Date(this.consumptions[i].timestamp)
          let consumptionDate = (new Date(d.getFullYear(), d.getMonth(), d.getDate(), d.getHours(), d.getMinutes() - d.getTimezoneOffset()).toISOString()).toString().substring(0,10)
          if(this.selectedDate == consumptionDate) {
            myDataPoints2[d.getHours()] = {"label": d.getHours(), "y":this.consumptions[i].hourlyConsumption}
          }
        }
      }
      let myDataPointsNew: { label: number, y: number }[] = [];
      this.myDataPoints = myDataPointsNew.concat(myDataPoints2)

      let newChart = {
        title: {
          text: "Energy Consumption " + this.selectedDate + " " + this.selectedDevice
        },
        theme: "light2",
        animationEnabled: true,
        exportEnabled: true,
        axisY: {
          includeZero: true,
          valueFormatString: "#####"
        },
        data: [{
          type: "area", //change type to bar, line, area, pie, etc
          yValueFormatString: "#####",
          color: "#ff7a30",
          dataPoints: this.myDataPoints
        }]
      }
      this.chartOptions = newChart
      this.cdr.detectChanges()
    }
  }

  connect() {
    let token = localStorage.getItem("tokenLogin")
    let tokenPayload: any;
    if (token != null) {
      tokenPayload = jwt_decode(token);
      this.ownerId = tokenPayload.id
    }
    this.connected = true
    var socket = new SockJS('http://localhost:8084/websocket');
    var stompClient = Stomp.over(socket);
    stompClient.connect({}, (frame) => {
      console.log('Connected: ' + frame);
      stompClient.subscribe('/notification/'+this.ownerId, (message: {body: string}): any => {
        let notif: Notification = JSON.parse(message.body)
        this._snackBar.open(notif.message, "close");
      });
    });
  }

}
