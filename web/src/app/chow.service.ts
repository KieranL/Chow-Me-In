import {Injectable} from '@angular/core';
import {Chow} from "./chow";
import {CHOWS} from "./mock-chows";
import {Observable} from "rxjs/Observable";
import {of} from "rxjs/observable/of";
import {HttpClient} from "@angular/common/http";
import {environment} from "../environments/environment";

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
}
