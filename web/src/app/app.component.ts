import {Component} from '@angular/core';
import {environment} from "../environments/environment";
import {UserService} from "./user.service";

@Component({
	selector: 'app-root',
	templateUrl: './app.component.html',
	styleUrls: ['./app.component.css']
})
export class AppComponent {
	title = 'Chow Me-In';
  authValid = false;

  constructor(private userService: UserService) {
  }

  login(event) {
		// Change location to cognito signin url
		// Callback URL must be fixed - whitelisted in AWS cognito console
		window.location.assign("https://chowmein.auth.us-east-2.amazoncognito.com/login?response_type=token&client_id=7pfks1vh7tn778mhqo107ha9g7&redirect_uri=https://chowme-in.com/login");
	}

  logout(event) {
    window.sessionStorage.removeItem('access_token');
    window.sessionStorage.removeItem('id_token');
    window.sessionStorage.removeItem('username');
    window.location.reload();
  }

  getUser() {
    return window.sessionStorage.getItem('username');
  }


  checkLoginValidity() {
    var token = window.sessionStorage.getItem("access_token");

    this.userService.getUsername(token).subscribe(
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
    
    this.authValid = this.getUser() != null;
  }

}