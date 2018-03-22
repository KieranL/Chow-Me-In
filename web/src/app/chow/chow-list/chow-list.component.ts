import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';
import {Component, OnInit} from '@angular/core';
import {ChowService} from "../chow.service";
import {Chow} from "../chow";

@Component({
  selector: 'app-chow-list',
  templateUrl: './chow-list.component.html',
  styleUrls: ['./chow-list.component.css']
})
export class ChowListComponent implements OnInit {
  chows: Chow[];
  selectedChow: Chow;
  showDetailPane: boolean;

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
    });
  }

  onSelect(chow: Chow): void {
    this.selectedChow = chow;
  }
}
