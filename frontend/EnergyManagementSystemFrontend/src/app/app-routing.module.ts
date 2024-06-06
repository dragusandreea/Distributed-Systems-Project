import { NgModule } from '@angular/core';
import {RouterModule, Routes} from "@angular/router";
import {HomePageComponent} from "./components/home-page/home-page.component";
import {LoginPageComponent} from "./components/login-page/login-page.component";
import {RegisterPageComponent} from "./components/register-page/register-page.component";
import {AdminHomePageComponent} from "./components/admin/admin-home-page/admin-home-page.component";
import {UsersManagePageComponent} from "./components/admin/users-manage-page/users-manage-page.component";
import {UsersQueryPageComponent} from "./components/admin/users-query-page/users-query-page.component";
import {DevicesManagePageComponent} from "./components/admin/devices-manage-page/devices-manage-page.component";
import {DevicesQueryPageComponent} from "./components/admin/devices-query-page/devices-query-page.component";
import {ClientHomePageComponent} from "./components/client/client-home-page/client-home-page.component";
import {AuthenticationGuardService} from "./services/authentication-guard.service";
import {ClientChartsPageComponent} from "./components/client/client-charts-page/client-charts-page.component";
import {ChatPageComponent} from "./components/chat-page/chat-page.component";

const routes: Routes = [
  {path:'welcome/home-page', component: HomePageComponent},
  {path:'welcome/login-page', component: LoginPageComponent},
  {path:'welcome/register-page', component: RegisterPageComponent},
  {path:'admin/admin-home-page', component:AdminHomePageComponent, canActivate: [AuthenticationGuardService]},
  {path:'admin/users-management-page', component:UsersManagePageComponent, canActivate: [AuthenticationGuardService]},
  {path:'admin/users-query-page', component:UsersQueryPageComponent, canActivate: [AuthenticationGuardService]},
  {path:'admin/devices-management-page', component:DevicesManagePageComponent, canActivate: [AuthenticationGuardService]},
  {path:'admin/devices-query-page', component:DevicesQueryPageComponent, canActivate: [AuthenticationGuardService]},
  {path:'client/client-home-page', component: ClientHomePageComponent, canActivate: [AuthenticationGuardService]},
  {path:'client/client-charts-page', component:ClientChartsPageComponent, canActivate: [AuthenticationGuardService]},
  {path:'chat-page', component:ChatPageComponent},
  {path:'**', component: HomePageComponent}
];
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
