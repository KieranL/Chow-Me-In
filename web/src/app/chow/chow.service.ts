import {Injectable} from '@angular/core';
import {Chow} from "./chow";
import {Observable} from "rxjs/Observable";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {UserService} from "../auth/user.service";

let httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable()
export class ChowService {
  private chowUrl = `${environment.serverUrl}/chow`;

  constructor(private http: HttpClient,
              private userService: UserService) {
  }

  getChows(): Observable<Chow[]> {
    return this.http.get<Chow[]>(this.chowUrl);
  }

  getChowById(chowId: number): Observable<Chow> {
    const url = `${this.chowUrl}/${chowId}`;
    return this.http.get<Chow>(url);
  }

  addChow(chow: Chow): Observable<Chow> {
    return this.http.post<Chow>(this.chowUrl, chow, httpOptions);
  }

  deleteChow(chowId: number): Observable<any> {
    const url = `${this.chowUrl}/${chowId}`;
    return this.http.delete(url, httpOptions);
  }

  editChow(chowId: number, chow: Chow): Observable<Chow> {
    const url = `${this.chowUrl}/${chowId}`;
    return this.http.post<Chow>(url, chow, httpOptions);
  }

  joinChow(chowId: number): Observable<Chow> {
    const url = `${this.chowUrl}/${chowId}/join`;
    this.refreshHeaders();
    return this.http.post<Chow>(url, {}, httpOptions);
  }

  private refreshHeaders(): void {
    httpOptions.headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Access-Token': this.userService.getAuthTokenFromStorage()
    });
  }
}
