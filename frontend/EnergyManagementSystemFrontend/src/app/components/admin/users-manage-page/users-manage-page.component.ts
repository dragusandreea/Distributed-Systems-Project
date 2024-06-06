import {Component, OnInit} from '@angular/core';
import {User, UserColumns, UserDetails} from "../../../models/User";
import {MatTableDataSource} from "@angular/material/table";
import {MatDialog} from "@angular/material/dialog";
import {UserService} from "../../../services/user.service";
import {HttpErrorResponse} from "@angular/common/http";

@Component({
  selector: 'app-users-manage-page',
  templateUrl: './users-manage-page.component.html',
  styleUrls: ['./users-manage-page.component.css']
})
export class UsersManagePageComponent implements OnInit {
  displayedColumns: string[] = UserColumns.map((col) => col.key)
  columnsSchema: any = UserColumns
  dataSource = new MatTableDataSource<User>()
  emailRegex = new RegExp('^[^@\\s]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,3}$');
  valid: any = {}

  constructor(public dialog: MatDialog, private userService: UserService) {}

  ngOnInit(): void {
    this.userService.getAllUsers().subscribe((res: any) => {
      this.dataSource.data = res
    }, (error: HttpErrorResponse) => {
      alert(error.error.message)
    })
  }
  transformUserDetailsToUser(userDetails: UserDetails): User {
    let user: User = new User();
    user.id = userDetails.id;
    user.name = userDetails.name;
    user.userType = userDetails.userType;
    user.username = userDetails.username;
    user.password = userDetails.password;

    return user;
  }
  editRow(row: UserDetails) {
    if (row.id == undefined) {
      let user: User = this.transformUserDetailsToUser(row);

      this.userService.addUser(user).subscribe((newUser: User) => {
        row.id = newUser.id
        row.isEdit = false
        row.isSelected = false;
      }, (error: HttpErrorResponse) => {
        let errors : string = error.error.message + '\n';
        if(error.error.errors.username != undefined) {
          errors = errors + error.error.errors.username + '\n'
        }
        if(error.error.errors.name != undefined) {
          errors = errors + error.error.errors.name + '\n'
        }
        if(error.error.errors.password != undefined) {
          errors = errors + error.error.errors.password + '\n'
        }
        alert(errors)
      })
    } else {
      let user: User = this.transformUserDetailsToUser(row);
      let validUsername = false;
      let validName = false;
      let validPassword = false;
      if(this.emailRegex.test(user.username)) {
        validUsername = true;
        this.userService.updateUser(user).subscribe(() => (row.isEdit = false), (error: HttpErrorResponse) => {
          alert(error.error.message)
        })
      } else {
        alert("Please use valid email address for username")
      }
      if(user.name != "") {
        validName = true
      } else {
        alert("Name cannot be empty")
      }
      if(user.password != "") {
        validPassword = true
      } else {
        alert("Password cannot be empty")
      }
      if(validUsername && validName && validPassword) {
        this.userService.updateUser(user).subscribe(() => {(row.isEdit = false)}, (error: HttpErrorResponse) => {
          alert(error.error.message)
        })
      }
    }
  }

  addRow() {
    const newRow: UserDetails = {
      id:undefined,
      name : '',
      username: '',
      password: '',
      userType:'',
      isEdit: true,
      isSelected: true,
    }
    this.dataSource.data = [newRow, ...this.dataSource.data]
  }

  removeRow(id: string) {
    this.userService.deleteUser(id).subscribe(() => {
      this.dataSource.data = this.dataSource.data.filter(
        (u: User) => u.id !== id,
      )
    }, (error: HttpErrorResponse) => { alert(error.error.message)})
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
