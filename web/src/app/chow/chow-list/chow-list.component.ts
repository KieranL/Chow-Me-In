import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import {Component, OnInit} from '@angular/core';
import {ChowService} from "../chow.service";
import {Chow} from "../chow";
import moment = require("moment");

@Component({
  selector: 'app-chow-list',
  templateUrl: './chow-list.component.html',
  styleUrls: ['./chow-list.component.css']
})
export class ChowListComponent implements OnInit {
  chows: Chow[];
  selectedChow: Chow;
  showDetailPane: boolean;
  allChows: Chow[];

  constructor(
    private spinnerService: Ng4LoadingSpinnerService,
    private chowService: ChowService) {
  }

  ngOnInit() {
    this.showDetailPane = false;
    this.spinnerService.show();
    this.getChows();
  }

  getChows(): void {
    this.chowService.getChows().subscribe((chows) => {
      this.spinnerService.hide();
      this.showDetailPane = true;
      this.chows = chows;
      this.allChows = chows;
    });
  }

  onSelect(chow: Chow): void {
    this.selectedChow = chow;
  }

  searchChows(searchString: string): void {
    let chowResults = [];
    if (!searchString) {
      this.chows = this.allChows;
    } else {
      for (let chow of this.allChows) {
        if (chow.food && chow.food.toLowerCase().indexOf(searchString.toLowerCase()) !== -1) {
          chowResults.push(chow);
        }
      }
      this.chows = chowResults;
    }
  }

  prettyDate(dateString: string): string {
    return this.chowService.prettyDate(dateString);
  }
}
