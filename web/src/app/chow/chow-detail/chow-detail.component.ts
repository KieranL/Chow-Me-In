import {Component, Input, OnInit} from '@angular/core';
import {Chow} from "../chow";
import {ChowService} from '../chow.service';

@Component({
  selector: 'app-chow-detail',
  templateUrl: './chow-detail.component.html',
  styleUrls: ['./chow-detail.component.css']
})
export class ChowDetailComponent implements OnInit {
  @Input() chow: Chow;

  constructor(private chowService: ChowService) {
  }

  ngOnInit() {
  }

  deleteChow(chowId: number): void {
    if (!chowId) {
      return;
    }
    this.chowService.deleteChow(chowId)
      .subscribe(() => {
        window.location.href = '/chow-list';
      });
  }
}
