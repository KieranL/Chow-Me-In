import {Component, Input, OnInit} from '@angular/core';
import {Chow} from "../chow";
import {ChowService} from '../chow.service';
import {UserService} from "../../auth/user.service";

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
}
