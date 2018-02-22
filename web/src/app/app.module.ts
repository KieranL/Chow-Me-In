import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {ChowListComponent} from './chow-list/chow-list.component';
import {ChowService} from './chow.service';
import {UserService} from './user.service';
import {HttpClientModule} from "@angular/common/http";
import {ChowDetailComponent} from './chow-detail/chow-detail.component';
import { ChowFormComponent } from './chow-form/chow-form.component';
import { AppRoutingModule } from './/app-routing.module';
import { LoginComponent } from './login/login.component';

@NgModule({
  declarations: [
    AppComponent,
    ChowListComponent,
    ChowDetailComponent,
    ChowFormComponent,
    LoginComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule
  ],
  providers: [ChowService, UserService],
  bootstrap: [AppComponent]
})
export class AppModule {
}
