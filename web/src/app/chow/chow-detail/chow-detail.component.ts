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

  constructor(private chowService: ChowService, private userService: UserService) {
  }

  ngOnInit() {
  }

  joinChow(chowId: number): void {
    if (!chowId) {
      return;
    } else {
      this.chowService.joinChow(chowId).subscribe();
    }
  }

  formatDate(date: string): string {
    if (moment(date).isValid()) {
      return moment.utc(date).local().format('MMMM DD, YYYY [at] h:mm a');
    } else {
      return date;
    }
  }
}
