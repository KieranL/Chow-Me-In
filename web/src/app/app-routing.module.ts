import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ChowFormComponent } from './chow-form/chow-form.component';
import { ChowListComponent } from './chow-list/chow-list.component';
import { LoginComponent } from './login/login.component';

const routes: Routes = [
  { path: '', redirectTo: '/chow-list', pathMatch: 'full' },
  { path: 'chow-list', component: ChowListComponent },
  { path: 'create-chow', component: ChowFormComponent },
  { path: 'login', component: LoginComponent }
];

@NgModule({
  imports: [ RouterModule.forRoot(routes) ],
  exports: [ RouterModule ]
})

export class AppRoutingModule { }
