import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree} from "@angular/router";
import {Observable} from "rxjs";
import jwtDecode from "jwt-decode";

@Injectable({
  providedIn: 'root'
})
export class AuthenticationGuardService implements CanActivate {
  routeURL: string = ""

  constructor(private router: Router) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    return new Promise((resolve, reject) => {
      this.routeURL = state.url;
      let token = localStorage.getItem("tokenLogin")
      if (token == null) {
        this.routeURL = 'welcome/login-page';
        this.router.navigate([this.routeURL]).then(r => {
        })
        return resolve(false);
      }

      let token_payload: any = jwtDecode(token)
      let userType = token_payload.userType;

      if (userType === 'ADMIN' && (!this.routeURL.includes('/admin') && !this.routeURL.includes('/welcome'))) {
        this.routeURL = 'admin/admin-home-page';
        this.router.navigate([this.routeURL]).then(r => {
        })
        return resolve(false);
      }

      if (userType === 'CLIENT' && (!this.routeURL.includes('/client') && !this.routeURL.includes('/welcome'))) {
        this.routeURL = 'client/client-home-page';
        this.router.navigate(['/client/client-home-page']).then(r => {
        })
        return resolve(false);
      }

      this.routeURL = this.router.url;
      return resolve(true);

    })
  }
}
