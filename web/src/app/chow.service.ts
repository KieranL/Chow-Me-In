import {Injectable} from '@angular/core';
import {Chow} from "./chow";
import {CHOWS} from "./mock-chows";
import {Observable} from "rxjs/Observable";
import {of} from "rxjs/observable/of";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {environment} from "../environments/environment";

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable()
export class ChowService {
  private chowUrl = `${environment.serverUrl}/chow`;

  constructor(private http: HttpClient) {
  }

  getChows(): Observable<Chow[]> {
    return of(CHOWS);
    // return this.http.get<Chow[]>(this.chowUrl);
    // TODO: replace mock data with real htp call
  }

  addChow (chow: Chow): Observable<Chow> {
    chow.id = 4;
    return of(chow);
    // TODO: replace w/ real http call
    // return this.http.post<Chow>(this.chowUrl, chow, httpOptions);
  }
}
