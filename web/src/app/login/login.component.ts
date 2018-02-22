import {Router, ActivatedRoute, Params} from '@angular/router';
import { Component, OnInit } from '@angular/core';
import {UserService} from "../user.service";
import {User} from "../user";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})

export class LoginComponent implements OnInit {
  userInfo: User;

  constructor(private userService: UserService) {
  }

  ngOnInit() {
    var params = this.userService.parseParams(window.location.hash.substr(1));

    window.sessionStorage.setItem("access_token", params['access_token']);
    window.sessionStorage.setItem("id_token", params['id_token']);

    this.userService.getUsername(params['access_token']).subscribe(
        data => {
          if(data['success'] == false) { 
            window.sessionStorage.removeItem('access_token');
            window.sessionStorage.removeItem('id_token');
            window.sessionStorage.removeItem('username');
          } else {
            window.sessionStorage.setItem('username', data['username']);
          }},
          err => {
            console.error(err);
          }        
        );
    window.location.href = '/chow-list';
  }

}
