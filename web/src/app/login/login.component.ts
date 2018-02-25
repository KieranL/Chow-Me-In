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
    var _this = this;

    // If incorrect # of params, redirect to chow-list
    if(Object.keys(params).length != 4) {
      this.router.navigate(['chow-list']);
    }

    window.localStorage.setItem("access_token", params['access_token']);
    window.localStorage.setItem("id_token", params['id_token']);

    this.userService.getUser(params['access_token']).subscribe(
      data => {
        if(data['success'] == false) { 
          window.localStorage.clear();
        } else {
          // Put this in a callback so that changes will be detected on login
          _this.ngZone.run(() => {
            // Set everything since this is async and tokens can be lost before we set these
            window.localStorage.setItem("access_token", params['access_token']);
            window.localStorage.setItem("id_token", params['id_token']);
            window.localStorage.setItem('username', data['user']['Username']);
            // It's like this because of the way it is... (blame boto3)
            window.localStorage.setItem('name', data['user']['UserAttributes'][2]['Value']);
          
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
