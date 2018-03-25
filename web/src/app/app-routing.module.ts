import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ChowFormComponent } from './chow/chow-form/chow-form.component';
import { ChowListComponent } from './chow/chow-list/chow-list.component';
import { LoginComponent } from './auth/login/login.component';
import { AuthGuardService as AuthGuard } from './auth/auth-guard.service';
import {MyChowsComponent} from "./chow/my-chows/my-chows.component";

const routes: Routes = [
  { path: '', redirectTo: '/chow-list', pathMatch: 'full' },
  { path: 'chow-list', component: ChowListComponent },
  { path: 'chow-form', component: ChowFormComponent, canActivate: [AuthGuard] },
  { path: 'chow-form/:id', component: ChowFormComponent, canActivate: [AuthGuard] },
  { path: 'my-chows', component: MyChowsComponent, canActivate: [AuthGuard] },
  { path: 'login', component: LoginComponent }
];

@NgModule({
  imports: [ RouterModule.forRoot(routes) ],
  exports: [ RouterModule ]
})

export class AppRoutingModule { }
