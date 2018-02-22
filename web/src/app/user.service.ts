import { Injectable } from '@angular/core';
import {Observable} from "rxjs/Observable";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {environment} from "../environments/environment";
import {User} from "./user";


@Injectable()
export class UserService {
	private userUrl = `${environment.serverUrl}/user`;
	public code: string;

	constructor(private http: HttpClient) {
	}

	getUsername(token: string): Observable<User> {
		return this.http.get<User>(this.userUrl + '/token/' + token);
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
