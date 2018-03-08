import {Component, NgZone} from '@angular/core';
import {Router} from '@angular/router';
import {environment} from "../environments/environment";
import {UserService} from "./auth/user.service";

@Component({
	selector: 'app-root',
	templateUrl: './app.component.html',
	styleUrls: ['./app.component.css']
})
export class AppComponent {
	title = 'Chow Me-In';

  constructor(private router: Router, private ngZone: NgZone, public userService: UserService) {
  }

  login(event) {
  	// Change location to cognito signin url
  	// Callback URL must be fixed - whitelisted in AWS cognito console
  	window.location.assign("https://chowmein.auth.us-east-2.amazoncognito.com/login?response_type=token&client_id=6s28e9fcoakqhdm5nrud946d5p&redirect_uri=https://chowme-in.com/login");
	}

  logout(event) {
    const _this = this;

    // Update authValid in ngZone.run() to trigger rerender
  	this.ngZone.run(() => {
	    window.sessionStorage.clear();
	    UserService.authValid = false;
      _this.router.navigate(['chow-list']);
	  });
  }

}