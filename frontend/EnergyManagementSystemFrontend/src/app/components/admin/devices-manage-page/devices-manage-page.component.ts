import {Component} from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import {MatDialog} from "@angular/material/dialog";
import {Device, DeviceColumns, DeviceDetails} from "../../../models/Device";
import {DeviceService} from "../../../services/device.service";
import {UserService} from "../../../services/user.service";
import {User} from "../../../models/User";
import {errorContext} from "rxjs/internal/util/errorContext";
import {HttpErrorResponse} from "@angular/common/http";

@Component({
  selector: 'app-devices-manage-page',
  templateUrl: './devices-manage-page.component.html',
  styleUrls: ['./devices-manage-page.component.css']
})
export class DevicesManagePageComponent {
  displayedColumns: string[] = DeviceColumns.map((col) => col.key)
  columnsSchema: any = DeviceColumns
  dataSource = new MatTableDataSource<Device>()
  valid: any = {}
  usernames: string[] = []
  ownerId: string = ""
  constructor(public dialog: MatDialog, private deviceService: DeviceService, private userService: UserService) {

  }

  ngOnInit(): void {
    let devices = this.deviceService.getAllDevices()
    if (devices != undefined) {
      devices.subscribe((res: any) => {
        this.dataSource.data = res
      }, error => alert(error))
    } else {
      alert("You do not have admin rights")
    }

    this.userService.getAllUsers().subscribe((res:any) => {
    for(let i = 0; i < res.length; i++) {
     this.usernames[i] = res[i].username
    }
    })

  }

  selectOwner(username: string) :void {
    this.userService.getUserByUsername(username).subscribe((user: User)=> {
      if(user.id != undefined) {
        this.ownerId = user.id
      }
    }, (error: HttpErrorResponse) => alert(error.error.message))
  }

  transformDeviceDetailsToDevice(deviceDetails: DeviceDetails): Device {
    let device: Device = new Device();
    device.id = deviceDetails.id;
    device.description = deviceDetails.description;
    device.address = deviceDetails.address;
    device.hourlyEnergyConsumptionLimit = deviceDetails.hourlyEnergyConsumptionLimit;
    deviceDetails.ownerId = this.ownerId
    device.ownerId = this.ownerId

    return device;
  }

  editRow(row: DeviceDetails) {
    if (row.id == undefined) {
      let device: Device = this.transformDeviceDetailsToDevice(row);
      let limit: number = 0;
      if(device.hourlyEnergyConsumptionLimit < limit) {
        window.alert("Hourly consumption Limit cannot be negative!")
      } else {
        let addedDevice = this.deviceService.addDevice(device)
        if (typeof (addedDevice) === 'string') {
          alert(addedDevice)
        } else {
          addedDevice.subscribe((newDevice: Device) => {
            row.id = newDevice.id
            row.isEdit = false
            if (typeof (newDevice.id) != 'undefined') {
              this.deviceService.addDeviceToSensorList(newDevice.id).subscribe((message: string) => {
                console.log(message)
              })
            }
          }, (error: HttpErrorResponse) => {
            let errors: string = error.error.message + '\n';
            if (error.error.errors.address != undefined) {
              errors = errors + error.error.errors.address + '\n'
            }
            if (error.error.errors.description != undefined) {
              errors = errors + error.error.errors.description + '\n'
            }
            if (error.error.errors.hourlyEnergyConsumptionLimit != undefined) {
              errors = errors + error.error.errors.hourlyEnergyConsumptionLimit + '\n'
            }
            alert(errors)
          })
        }
      }
    } else {
      let device: Device = this.transformDeviceDetailsToDevice(row);
      let updatedDevice = this.deviceService.updateDevice(device)
      if (typeof(updatedDevice) === 'string') {
        alert(updatedDevice)
      } else {
        updatedDevice.subscribe(() => {row.ownerId = this.ownerId; row.isEdit = false}, (error: HttpErrorResponse) => {
          alert(error.error.message)
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
}
