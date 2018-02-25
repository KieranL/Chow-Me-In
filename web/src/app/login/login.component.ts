import {Router} from '@angular/router';
import {Component, OnInit, NgZone} from '@angular/core';
import {UserService} from "../user.service";
import {User} from "../user";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})

export class LoginComponent implements OnInit {

  constructor(private router: Router, private ngZone: NgZone, private userService: UserService) {
  }

  ngOnInit() {
    var params = this.userService.parseParams(window.location.hash.substr(1));
    const _this = this;

    // If incorrect # of params, redirect to chow-list
    if(Object.keys(params).length != 4) {
      this.router.navigate(['chow-list']);
    }

    window.sessionStorage.setItem("access_token", params['access_token']);
    window.sessionStorage.setItem("id_token", params['id_token']);

    this.userService.getUserFromToken(params['access_token']).subscribe(
      data => {
        if(data['success'] == false) { 
          window.sessionStorage.clear();
        } else {
          // Put this in another callback so that changes will be detected on login
          _this.ngZone.run(() => {
            // Set everything since this is async and tokens can be lost before we set these
            window.sessionStorage.setItem("access_token", params['access_token']);
            window.sessionStorage.setItem("id_token", params['id_token']);
            window.sessionStorage.setItem('username', _this.userService.getUsernameFromObject(data));
            window.sessionStorage.setItem('name', _this.userService.getUsersNameFromObject(data));
          
            // This changes the value of UserService.authValid which then triggers a rerender
            _this.userService.verifyLoginValidity();
          });
        }},
        err => {
          console.error(err);
        }
    );

    // Change routes immediately, changes will update later (async)
    this.router.navigate(['chow-list']);
  }

}
