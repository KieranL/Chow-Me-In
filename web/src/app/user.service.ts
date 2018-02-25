import {Injectable, NgZone} from '@angular/core';
import {Observable} from "rxjs/Observable";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {environment} from "../environments/environment";
import {User} from "./user";


@Injectable()
export class UserService {
	private userUrl = `${environment.serverUrl}/user`;
	public static authValid;

	constructor(private http: HttpClient) {
	}

	getUser(tok: string): Observable<User> {
    var token = tok != null ? tok : "INVALIDTOKEN";
    return this.http.get<User>(this.userUrl + '/token/' + token);
  }

  verifyLoginValidity() {
    var token = window.localStorage.getItem("access_token");
    this.getUser(token).subscribe(
      data => {
        if(data['success'] == false) {
          window.localStorage.clear();
        } else {
          window.localStorage.setItem('username', data['user']['Username']);
          // It's like this because of the way it is... (blame boto3)
          window.localStorage.setItem('name', data['user']['UserAttributes'][2]['Value']);
        }
      },
      err => {
        console.error(err);
      }
    );

    UserService.authValid = this.getUsername() != null;
  }

  getValidity(){
    return UserService.authValid;
  }

  getUsername() {
    return window.localStorage.getItem('username');
  }

  getUsersName() {
    return window.localStorage.getItem('name');
  }

	// Converts a full URI into a JSON mapping of parameters->values
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
