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

  constructor(private chowService: ChowService) {
  }

  ngOnInit() {
    this.getChows();
  }

  getChows(): void {
    this.chowService.getChows().subscribe((chows) => {
      this.chows = chows;
    });
  }

  onSelect(chow: Chow): void {
    this.selectedChow = chow;
  }
}
