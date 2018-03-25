import {Router} from '@angular/router';
import {Component, Input, OnInit} from '@angular/core';
import {Chow} from "../chow";
import {ChowService} from '../chow.service';
import {UserService} from "../../auth/user.service";
import moment = require("moment");

@Component({
  selector: 'app-chow-detail',
  templateUrl: './chow-detail.component.html',
  styleUrls: ['./chow-detail.component.css']
})
export class ChowDetailComponent implements OnInit {
  @Input() chow: Chow;
  username;
  token;

  constructor(
    private router: Router,
    private userService: UserService,
    private chowService: ChowService
  ) {
  }

  ngOnInit() {
    this.username = this.userService.getUsernameFromStorage();
    this.token = this.userService.getAuthTokenFromStorage();
  }

  deleteChow(chowId: number): void {
    if (!chowId) {
      return;
    }
    this.chowService.deleteChow(chowId)
      .subscribe(() => {
        this.router.navigate(['chow-list']);
      });
  }

  joinChow(chowId: number): void {
    if (!chowId) {
      return;
    } else {
      this.chowService.joinChow(chowId).subscribe(() => {
        this.router.navigate(['my-chows']);
      });
    }
  }

  prettyDate(dateString: string): string {
    return this.chowService.prettyDate(dateString);
  }
}
