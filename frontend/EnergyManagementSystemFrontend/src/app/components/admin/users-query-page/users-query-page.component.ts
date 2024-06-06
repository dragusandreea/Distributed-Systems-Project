import {Component, OnInit} from '@angular/core';
import {MatDialog} from "@angular/material/dialog";
import {UserService} from "../../../services/user.service";
import {User, UserColumns, UserDetails} from "../../../models/User";
import {MatTableDataSource} from "@angular/material/table";
import {ApiException} from "../../../models/ApiException";
import {HttpErrorResponse} from "@angular/common/http";

@Component({
  selector: 'app-users-query-page',
  templateUrl: './users-query-page.component.html',
  styleUrls: ['./users-query-page.component.css']
})
export class UsersQueryPageComponent implements OnInit {
  displayedColumns: string[] = UserColumns.map((col) => col.key)
  columnsSchema: any = UserColumns
  dataSource = new MatTableDataSource<User>()
  valid: any = {}
  queryMethod: string = "";
  queryParam: string = "";
  id: number = 0;

  constructor(public dialog: MatDialog, private userService: UserService) {
  }

  ngOnInit(): void {
    this.userService.getAllUsers().subscribe((res: any) => {
        this.dataSource.data = res
      }, (error: HttpErrorResponse) => {
        alert(error.error.message)
      }
    )
  }

  changeQuery(query: string) {
    this.queryMethod = query;
  }

  queryUserDataBase() {
    if (this.queryMethod === "findById") {
      this.userService.getUserById(this.queryParam).subscribe((foundUser: User) => this.dataSource.data = [foundUser],
        (error: HttpErrorResponse) => {
          alert(error.error.message)
        })
    }

    if (this.queryMethod === "findByName") {
      this.userService.getUserByName(this.queryParam).subscribe((foundUsers: User[]) => this.dataSource.data = foundUsers,
        (error: HttpErrorResponse) => {
          alert(error.error.message)
        })
    }

    if (this.queryMethod === "findByUsername") {
      this.userService.getUserByUsername(this.queryParam).subscribe((foundUser: User) => this.dataSource.data = [foundUser],
        (error: HttpErrorResponse) => {
          alert(error.error.message)
        })
    }

    if (this.queryMethod === "findAll") {
      this.userService.getAllUsers().subscribe((foundUsers: User[]) => this.dataSource.data = foundUsers,
        (error: HttpErrorResponse) => {
          alert(error.error.message)
        })
    }

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
      })
    } else {
      let user: User = this.transformUserDetailsToUser(row);
      this.userService.updateUser(user).subscribe(() => (row.isEdit = false))
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
