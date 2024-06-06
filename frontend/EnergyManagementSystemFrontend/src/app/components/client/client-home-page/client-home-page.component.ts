import {Component, OnInit} from '@angular/core';
import {Device, DeviceColumns, DeviceDetails} from "../../../models/Device";
import {MatTableDataSource} from "@angular/material/table";
import {User} from "../../../models/User";
import {MatDialog} from "@angular/material/dialog";
import {DeviceService} from "../../../services/device.service";
import jwt_decode from "jwt-decode";
import * as SockJS from 'sockjs-client';
import * as Stomp from 'stompjs';
import {Notification} from "../../../models/Notification";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-client-home-page',
  templateUrl: './client-home-page.component.html',
  styleUrls: ['./client-home-page.component.css']
})
export class ClientHomePageComponent implements OnInit {
  displayedColumns: string[] = DeviceColumns.map((col) => col.key)
  columnsSchema: any = DeviceColumns
  dataSource = new MatTableDataSource<Device>()
  dataSource2 = new MatTableDataSource<Device>()
  valid: any = {}
  queryMethod: string = "";
  queryParam: string = "";
  id: number = 0;
  ownerId: string = ""
  users: User[] = []
  filteredDevices: Device[] = []
  connected: boolean = false
  constructor(public dialog: MatDialog, private deviceService: DeviceService, private _snackBar: MatSnackBar) {
  }

  ngOnInit(): void {
    this.connect()
    //this.connectNotifications()
    let token = localStorage.getItem("tokenLogin")
    let tokenPayload: any;
    if (token != null) {
      tokenPayload = jwt_decode(token);
      this.ownerId = tokenPayload.id
    }

    let res = this.deviceService.getDeviceByOwnerId(this.ownerId);
    if (typeof (res) != 'undefined') {
      res.subscribe((res: any) => {
        this.dataSource.data = res
        this.dataSource2.data = res
      })
    }

  }

  changeQuery(query: string) {
    this.queryMethod = query;
  }

  queryUserDataBase() {
    if (this.queryMethod === "findById") {
      let cnt = 0
      this.filteredDevices = []
      for (let i = 0; i < this.dataSource2.data.length; i++) {
        if (this.dataSource2.data[i].id === this.queryParam) {
          this.filteredDevices[cnt++] = this.dataSource2.data[i]
        }
      }
      this.dataSource.data = this.filteredDevices;
    }

    if (this.queryMethod === "findByAddress") {
      let cnt = 0
      this.filteredDevices = []
      for (let i = 0; i < this.dataSource2.data.length; i++) {
        if (this.dataSource2.data[i].address === this.queryParam) {
          this.filteredDevices[cnt++] = this.dataSource2.data[i]
        }
      }
      this.dataSource.data = this.filteredDevices;
      console.log(this.filteredDevices)
    }

    if (this.queryMethod === "findByDescription") {
      let cnt = 0
      this.filteredDevices = []
      for (let i = 0; i < this.dataSource2.data.length; i++) {
        if (this.dataSource2.data[i].description === this.queryParam) {
          this.filteredDevices[cnt++] = this.dataSource2.data[i]
        }
      }
      this.dataSource.data = this.filteredDevices;
    }

    if (this.queryMethod === "findByHourlyLimit") {
      this.filteredDevices = []
      let cnt = 0
      for (let i = 0; i < this.dataSource2.data.length; i++) {
        if (this.dataSource2.data[i].hourlyEnergyConsumptionLimit === Number.parseInt(this.queryParam)) {
          this.filteredDevices[cnt++] = this.dataSource2.data[i]
        }
      }
      this.dataSource.data = this.filteredDevices;
    }

    if (this.queryMethod === "findByHourlyLimitLessThan") {
    this.filteredDevices = []
      let cnt = 0
      for (let i = 0; i < this.dataSource2.data.length; i++) {
        if ( this.dataSource2.data[i].hourlyEnergyConsumptionLimit < Number.parseInt(this.queryParam)) {
          this.filteredDevices[cnt++] =  this.dataSource2.data[i]
        }
      }
      this.dataSource.data = this.filteredDevices;
    }

    if (this.queryMethod === "findByHourlyLimitGreaterThan") {
     this.filteredDevices = []
      let cnt = 0
      for (let i = 0; i >  this.dataSource2.data.length; i++) {
        if ( this.dataSource2.data[i].hourlyEnergyConsumptionLimit < Number.parseInt(this.queryParam)) {
          this.filteredDevices[cnt++] =  this.dataSource2.data[i]
        }
      }
      this.dataSource.data = this.filteredDevices;
    }

    if (this.queryMethod === "findAll") {
      this.dataSource.data =  this.dataSource2.data
    }

    if (this.queryMethod === "findByOwnerId") {
      this.dataSource.data =  this.dataSource2.data
    }

  }

  transformDeviceDetailsToDevice(deviceDetails: DeviceDetails): Device {
    let device: Device = new Device();
    device.id = deviceDetails.id;
    device.address = deviceDetails.address;
    device.description = deviceDetails.description;
    device.hourlyEnergyConsumptionLimit = deviceDetails.hourlyEnergyConsumptionLimit;
    device.ownerId = deviceDetails.ownerId;

    return device;
  }

  editRow(row: DeviceDetails) {
    if (row.id == undefined) {
      let device: Device = this.transformDeviceDetailsToDevice(row);
      let addedDevice = this.deviceService.addDevice(device)
      if (typeof (addedDevice) === 'string') {
        alert(addedDevice)
      } else {
        addedDevice.subscribe((newDevice: Device) => {
          row.id = newDevice.id
          row.isEdit = false
        })
      }
    } else {
      let device: Device = this.transformDeviceDetailsToDevice(row);
      let updatedDevice = this.deviceService.updateDevice(device)
      if (typeof (updatedDevice) === 'string') {
        alert(updatedDevice)
      } else {
        updatedDevice.subscribe(() => {
          row.ownerId = this.ownerId;
          row.isEdit = false
        })
      }
    }
  }

  addRow() {
    const newRow: DeviceDetails = {
      id: undefined,
      description: '',
      address: '',
      hourlyEnergyConsumptionLimit: 0,
      ownerId: '',
      isEdit: true,
    }
    this.dataSource.data = [newRow, ...this.dataSource.data]
  }

  removeRow(id: string) {
    let deletedMessage = this.deviceService.deleteDeviceById(id)
    if (deletedMessage != undefined) {
      deletedMessage.subscribe(() => {
        this.dataSource.data = this.dataSource.data.filter(
          (d: Device) => d.id !== id,
        )
      })
    } else {
      alert("You do not have admin rights")
    }
  }

  inputHandler(e: any, id: number, key: string) {
    if (!this.valid[id]) {
      this.valid[id] = {}
    }
    this.valid[id][key] = e.target.validity.valid
  }

  disableSubmit(id: number) {
    if (this.valid[id]) {
      return Object.values(this.valid[id]).some((item) => item === false)
    }
    return false
  }

  logOut() {
    localStorage.clear()
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

  // connectNotifications() {
  //   let token = localStorage.getItem("tokenLogin")
  //   let tokenPayload: any;
  //   if (token != null) {
  //     tokenPayload = jwt_decode(token);
  //     this.ownerId = tokenPayload.id
  //   }
  //   var socket = new SockJS('http://localhost:8085/notification');
  //   var stompClient = Stomp.over(socket);
  //   stompClient.connect({}, (frame) => {
  //     console.log('Connected: ' + frame);
  //     stompClient.subscribe('/chat-notification/'+this.ownerId, (message: {body: string}): any => {
  //       let notif: Notification = JSON.parse(message.body)
  //       this._snackBar.open(notif.message, "close");
  //
  //     });
  //   });
  // }
}
