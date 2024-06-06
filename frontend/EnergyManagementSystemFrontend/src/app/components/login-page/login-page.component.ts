import {Component, OnInit} from '@angular/core';
import {User} from "../../models/User";
import {UserService} from "../../services/user.service";
import {Router} from "@angular/router";
import jwt_decode from "jwt-decode";
import {HttpErrorResponse} from "@angular/common/http";
import {ApiException} from "../../models/ApiException";

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.css']
})
export class LoginPageComponent implements OnInit {
  user: User = new User();

  constructor(private userService: UserService,
              private router: Router) {
  }

  submitLogInForm(): void {
    this.userService
      .login(this.user.username, this.user.password)
      .subscribe((response: any) => {
        localStorage.setItem('tokenLogin', response.token);
        let savedToken: any;
        savedToken = localStorage.getItem('tokenLogin')
        let tokenPayload: any;
        tokenPayload = jwt_decode(savedToken);
        const userType = tokenPayload.userType;

        if (userType === 'CLIENT') {
          this.router.navigate(['/client/client-home-page']);

        }
        if (userType === 'ADMIN') {
          this.router.navigate(['/admin/admin-home-page']);
        }
      })
  }

  ngOnInit(): void {
  }

}
