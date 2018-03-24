import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {ChowListComponent} from './chow/chow-list/chow-list.component';
import {ChowService} from './chow/chow.service';
import {UserService} from './auth/user.service';
import {AuthGuardService} from './auth/auth-guard.service';
import {HttpClientModule} from "@angular/common/http";
import {ChowDetailComponent} from './chow/chow-detail/chow-detail.component';
import { ChowFormComponent } from './chow/chow-form/chow-form.component';
import { AppRoutingModule } from './/app-routing.module';
import { LoginComponent } from './auth/login/login.component';
import { Ng4LoadingSpinnerModule } from 'ng4-loading-spinner';
import { MaterialModule } from './material/material.module';
import { MyChowsComponent } from './chow/my-chows/my-chows.component';

@NgModule({
  declarations: [
    AppComponent,
    ChowListComponent,
    ChowDetailComponent,
    ChowFormComponent,
    LoginComponent,
    MyChowsComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    Ng4LoadingSpinnerModule.forRoot(),
    MaterialModule
  ],
  providers: [ChowService, UserService, AuthGuardService],
  bootstrap: [AppComponent]
})
export class AppModule {
}
