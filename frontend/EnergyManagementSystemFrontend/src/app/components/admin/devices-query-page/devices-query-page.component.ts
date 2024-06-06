import { Component } from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import {MatDialog} from "@angular/material/dialog";
import {UserService} from "../../../services/user.service";
import {Device, DeviceColumns, DeviceDetails} from "../../../models/Device";
import {DeviceService} from "../../../services/device.service";
import {User} from "../../../models/User";
import {HttpErrorResponse} from "@angular/common/http";

@Component({
  selector: 'app-devices-query-page',
  templateUrl: './devices-query-page.component.html',
  styleUrls: ['./devices-query-page.component.css']
})
export class DevicesQueryPageComponent {
  displayedColumns: string[] = DeviceColumns.map((col) => col.key)
  columnsSchema: any = DeviceColumns
  dataSource = new MatTableDataSource<Device>()
  valid: any = {}
  queryMethod: string = "";
  queryParam: string = "";
  id: number = 0;
  ownerId: string = ""
  users: User[]= []
  usernames: string[] = []

  constructor(public dialog: MatDialog, private deviceService: DeviceService, private userService: UserService) {}

  ngOnInit(): void {
    let res = this.deviceService.getAllDevices();
    if(typeof(res) != 'undefined') {
    res.subscribe((res: any) => {
        this.dataSource.data = res
      })
    }

    this.userService.getAllUsers().subscribe((res:any) => {
      for(let i = 0; i < res.length; i++) {
        this.usernames[i] = res[i].username
      }
    }, (error: HttpErrorResponse) => {console.log(error.error.message)})
  }

  changeQuery(query: string) {
    this.queryMethod = query;
  }

  queryUserDataBase() {
    if(this.queryMethod === "findById") {
      let device =  this.deviceService.getDeviceById(this.queryParam);
      if(typeof(device) === 'undefined') {
        alert(device)
      } else {
        device.subscribe((foundDevice: Device) => this.dataSource.data = [foundDevice])
      }
    }

    if(this.queryMethod === "findByAddress") {
      let res = this.deviceService.getDeviceByAddress(this.queryParam);
      if(typeof(res) != 'undefined') {
        res.subscribe((res: any) => {
          this.dataSource.data = res
        })
      }
    }

    if(this.queryMethod === "findByDescription") {
      let res = this.deviceService.getDeviceByDescription(this.queryParam);
      if(typeof(res) != 'undefined') {
        res.subscribe((res: any) => {
          this.dataSource.data = res
        })
      }
    }

    if(this.queryMethod === "findByHourlyLimit") {
      let res = this.deviceService.getDeviceByHourlyLimit(Number.parseInt(this.queryParam));
      if(typeof(res) != 'undefined') {
        res.subscribe((res: any) => {
          this.dataSource.data = res
        })
      }
    }

    if(this.queryMethod === "findByHourlyLimitLessThan") {
      let res = this.deviceService.getDeviceByHourlyLimitLessThan(Number.parseInt(this.queryParam));
      if(typeof(res) != 'undefined') {
        res.subscribe((res: any) => {
          this.dataSource.data = res
        })
      }
    }

    if(this.queryMethod === "findByHourlyLimitGreaterThan") {
      let res = this.deviceService.getDeviceByHourlyLimitGreaterThan(Number.parseInt(this.queryParam));
      if(typeof(res) != 'undefined') {
        res.subscribe((res: any) => {
          this.dataSource.data = res
        })
      }
    }

    if(this.queryMethod === "findAll") {
      let res = this.deviceService.getAllDevices();
      if(typeof(res) != 'undefined') {
        res.subscribe((res: any) => {
          this.dataSource.data = res
        })
      }
    }
    if(this.queryMethod === "findByOwnerId") {

      let res = this.deviceService.getDeviceByOwnerId(this.ownerId);
      if(typeof(res) != 'undefined') {
        res.subscribe((res: any) => {
          this.dataSource.data = res
        })
      }
    }

  }

  selectOwner(username: string) :void {
    this.userService.getUserByUsername(username).subscribe((user: User) => {
      if(user.id != undefined) {
        this.ownerId = user.id
      }
    }, (error: HttpErrorResponse) => alert(error.error.message))

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
      if (typeof(updatedDevice) === 'string') {
        alert(updatedDevice)
      } else {
        updatedDevice.subscribe(() => {row.ownerId = this.ownerId; row.isEdit = false})
      }
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
}
