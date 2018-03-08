import {Injectable} from '@angular/core';
import {Router, CanActivate} from '@angular/router';
import {UserService} from './user.service';

@Injectable()
export class AuthGuardService implements CanActivate {
  constructor(private auth: UserService, private router: Router) {}

  canActivate(): boolean {
    this.auth.verifyLoginValidity();
    var valid = this.auth.getValidity();

    // Verify validity of the user token, redirect if necessary
    if (valid === false) {
      this.router.navigate(['chow-list']);
      return false;
    }

    return true;
  }
}