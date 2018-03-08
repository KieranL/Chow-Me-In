import {Injectable} from '@angular/core';
import {Chow} from "./chow";
import {Observable} from "rxjs/Observable";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {environment} from "../../environments/environment";

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable()
export class ChowService {
  private chowUrl = `${environment.serverUrl}/chow`;

  constructor(private http: HttpClient) {
  }

  getChows(): Observable<Chow[]> {
    return this.http.get<Chow[]>(this.chowUrl);
  }

  addChow(chow: Chow): Observable<Chow> {
    return this.http.post<Chow>(this.chowUrl, chow, httpOptions);
  }

  deleteChow(chowId: number): Observable<{}> {
    const url = `${this.chowUrl}/${chowId}`;
    return this.http.delete(url, httpOptions);
  }
}
