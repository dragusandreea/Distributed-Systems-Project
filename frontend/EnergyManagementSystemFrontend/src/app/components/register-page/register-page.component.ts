import { Component } from '@angular/core';
import {UserService} from "../../services/user.service";
import {User} from "../../models/User";
import {HttpErrorResponse} from "@angular/common/http";

@Component({
  selector: 'app-register-component',
  templateUrl: './register-page.component.html',
  styleUrls: ['./register-page.component.css']
})
export class RegisterPageComponent {
  adminType: boolean = false;
  clientType: boolean = false;


  name:string = "";
  username: string = "";
  password: string = "";
  userType: string = "";

  emailRegex = new RegExp('^[^@\\s]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,3}$');
  passwordRegex = new RegExp('^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[!@#$%^&*\.]).{10,}$');

  validUserName:boolean= false;
  validPassword:boolean = false;
  validUserType:boolean = false;
  validName:boolean = false;
  constructor(private userService: UserService) {

  }

  onItemChange(value: string) {
    if(value === 'clientType') {
      this.userType = 'CLIENT';
      this.clientType = true;
      this.adminType = false;
    }

    if(value === 'adminType') {
      this.userType = 'ADMIN';
      this.clientType = false;
      this.adminType = true;
    }
  }

  submitRegisterForm() {
    this.validUserName = this.emailRegex.test(<string>this.username);
    this.validPassword = this.passwordRegex.test(<string>this.password);

    this.validUserType = this.userType != "";
    this.validName = this.name != "";


    if(!this.validName) {
      alert("Name cannot be empty");
    }

    if(!this.validUserName) {
      alert("Please use valid email address for username")
    }

     if(!this.validPassword) {
       alert("Invalid Password");
     }

    if(!this.validUserType) {
      alert("Please select user type");
    }

    if(this.validUserType && this.validUserName && this.validName && this.validPassword) {
        let user: User = new User();
        user.id = undefined;
        user.name = this.name;
        user.username = this.username;
        user.password = this.password;
        user.userType = this.userType;
        this.userService.register(user).subscribe((token: String) => {
          console.log(token)
        }, (error: HttpErrorResponse) => { alert(error.error.message)})

      location.href = '/welcome/login-page';
    }

  }
}
