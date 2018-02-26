import {Injectable, NgZone} from '@angular/core';
import {Observable} from "rxjs/Observable";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {User} from "./user";

@Injectable()
export class UserService {
	private userUrl = `${environment.serverUrl}/user`;

  // Should never be used for verifying auth, used only for view components
  public static authValid = false;

  constructor(private http: HttpClient) {
  }

  // Grabs entire user object directly from boto3
  getUserFromToken(tok: string): Observable<User> {
    var token = tok != null ? tok : "INVALIDTOKEN";
    return this.http.get(this.userUrl + '/token/' + token);
  }

  // Used between page loads to verify sessionStorage variables are valid, used only by views
  verifyLoginValidity() {
    const _this = this;
    var token = this.getAuthTokenFromStorage();

    this.getUserFromToken(token).subscribe(
      data => {
        if(data['success'] == false) {
          UserService.authValid = false;
          window.sessionStorage.clear();
        } else {
          UserService.authValid = true;
          window.sessionStorage.setItem('username', _this.getUsernameFromObject(data));
          window.sessionStorage.setItem('name', _this.getUsersNameFromObject(data));
        }
      },
      err => {
        console.error(err);
      }
    );

    UserService.authValid = this.getUsernameFromStorage() != null;
  }

  // Used between page loads to verify sessionStorage variables are valid, used only by views
  getValidity(){
    return UserService.authValid;
  }

  // Grab the auth token, this is pretty much the password and is used to verify identity
  getAuthTokenFromStorage() {
    return window.sessionStorage.getItem('access_token');
  }

  // Grab the cached values from storage, may not be up to date. For up to date values, see *FromObject()
  getUsernameFromStorage() {
    return window.sessionStorage.getItem('username');
  }

  // Grab the cached values from storage, may not be up to date. For up to date values, see *FromObject()
  getUsersNameFromStorage() {
    return window.sessionStorage.getItem('name');
  }

  // Grab the values from a user object, call getUserFromToken() for the object. OK for verifying identity.
  getUsernameFromObject(user) {
    return user['user']['Username'];
  }

  // Grab the values from a user object, call getUserFromToken() for the object. OK for verifying identity.
  getUsersNameFromObject(user) {
    return user['user']['UserAttributes'][2]['Value'];
  }

  // Grab the values from a user object, call getUserFromToken() for the object. OK for verifying identity.
  getUsersEmailFromObject(user) {
    return user['user']['UserAttributes'][3]['Value'];
  }

	// Parse params from params-only URI into a JSON mapping of parameters->values
	parseParams(str) {
		var pieces = str.split("&"), data = {}, i, parts;
  	
    // process each query pair
  	for (i = 0; i < pieces.length; i++) {
      parts = pieces[i].split("=");
      if (parts.length < 2) {
        parts.push("");
      }
      data[decodeURIComponent(parts[0])] = decodeURIComponent(parts[1]);
    }
    return data;
  }

}
