import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {ChowListComponent} from './chow-list/chow-list.component';
import {ChowService} from './chow.service';
import {HttpClientModule} from "@angular/common/http";

@NgModule({
  declarations: [
    AppComponent,
    ChowListComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule
  ],
  providers: [ChowService],
  bootstrap: [AppComponent]
})
export class AppModule {
}
