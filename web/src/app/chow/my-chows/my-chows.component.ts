import {Component, OnInit} from '@angular/core';
import {Chow} from "../chow";
import {Ng4LoadingSpinnerService} from "ng4-loading-spinner";
import {ChowService} from "../chow.service";

@Component({
  selector: 'app-my-chows',
  templateUrl: './my-chows.component.html',
  styleUrls: ['./my-chows.component.css']
})
export class MyChowsComponent implements OnInit {
  postedChows: Chow[];
  joinedChows: Chow[];

  constructor(private spinnerService: Ng4LoadingSpinnerService,
              private chowService: ChowService) {
  }

  ngOnInit() {
    this.spinnerService.show();
    this.getChows();
  }

  getChows(): void {
    this.chowService.getPostedChows().subscribe((postedChows) => {
      this.postedChows = postedChows;
      this.chowService.getJoinedChows().subscribe((joinedChows) => {
        this.joinedChows = joinedChows;
        this.spinnerService.hide();
      });
    });
  }
}
