import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { HomePageComponent } from './components/home-page/home-page.component';
import {AppRoutingModule} from "./app-routing.module";
import { LoginPageComponent } from './components/login-page/login-page.component';
import {FormsModule} from "@angular/forms";
import { RegisterPageComponent } from './components/register-page/register-page.component';
import { AdminHomePageComponent } from './components/admin/admin-home-page/admin-home-page.component';
import { UsersManagePageComponent } from './components/admin/users-manage-page/users-manage-page.component';
import {MatTableModule} from "@angular/material/table";
import {MatCheckboxModule} from "@angular/material/checkbox";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatSelectModule} from "@angular/material/select";
import {MatDialogModule} from "@angular/material/dialog";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {MatInputModule} from "@angular/material/input";
import {MatButtonModule} from "@angular/material/button";
import {MatDividerModule} from "@angular/material/divider";
import { UsersQueryPageComponent } from './components/admin/users-query-page/users-query-page.component';
import { DevicesManagePageComponent } from './components/admin/devices-manage-page/devices-manage-page.component';
import {HttpClientModule} from "@angular/common/http";
import { DevicesQueryPageComponent } from './components/admin/devices-query-page/devices-query-page.component';
import { ClientHomePageComponent } from './components/client/client-home-page/client-home-page.component';
import * as SockJS from "sockjs-client";
import * as Stomp from 'stompjs';
import { ClientChartsPageComponent } from './components/client/client-charts-page/client-charts-page.component';
import {CanvasJSAngularChartsModule} from "@canvasjs/angular-charts";
import {MatDatepickerModule} from "@angular/material/datepicker";
import {MatNativeDateModule} from "@angular/material/core";
import {MatSnackBarModule} from "@angular/material/snack-bar";
import { ChatPageComponent } from './components/chat-page/chat-page.component';

@NgModule({
  declarations: [
    AppComponent,
    HomePageComponent,
    LoginPageComponent,
    RegisterPageComponent,
    AdminHomePageComponent,
    UsersManagePageComponent,
    UsersQueryPageComponent,
    DevicesManagePageComponent,
    DevicesQueryPageComponent,
    ClientHomePageComponent,
    ClientChartsPageComponent,
    ChatPageComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    FormsModule,
    MatTableModule,
    MatCheckboxModule,
    MatFormFieldModule,
    MatSelectModule,
    MatDialogModule,
    MatInputModule,
    BrowserAnimationsModule,
    MatButtonModule,
    MatDividerModule,
    CanvasJSAngularChartsModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatSnackBarModule
  ],
  providers: [MatDatepickerModule
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
