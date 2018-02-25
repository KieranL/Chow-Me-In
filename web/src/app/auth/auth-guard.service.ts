import {Injectable} from '@angular/core';
import {Router, CanActivate} from '@angular/router';
import {UserService} from './user.service';

@Injectable()
export class AuthGuardService implements CanActivate {
  constructor(private auth: UserService, private router: Router) {}

  canActivate(): boolean {
    if (!this.auth.getValidity()) {
      this.router.navigate(['chow-list']);
      return false;
    }
    return true;
  }
}