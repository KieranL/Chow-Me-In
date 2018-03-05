import {Component, Input, OnInit} from '@angular/core';
import {ChowService} from '../chow.service';
import {Chow} from '../chow';

@Component({
  selector: 'app-chow-form',
  templateUrl: './chow-form.component.html',
  styleUrls: ['./chow-form.component.css']
})
export class ChowFormComponent implements OnInit {
  @Input() chow: Chow;

  constructor(private chowService: ChowService) {
  }

  ngOnInit() {
  }

  add(food: string, meetLocation: string, meetTime: string, notes: string): void {
    let newChow: Chow = {
      lastUpdated: (new Date()).toISOString().split('.')[0]
    };
    if (food !== "") {
      newChow.food = food;
    }
    if (meetLocation !== "") {
      newChow.meetLocation = meetLocation;
    }
    if (meetTime !== "") {
      newChow.meetTime = meetTime;
    }
    if (notes !== "") {
      newChow.notes = notes;
    }
    this.chowService.addChow(newChow)
      .subscribe(chow => {
        window.location.href = '/chow-list';
      });
  }

  save(food: string, meetLocation: string, meetTime: string, notes: string): void {
    let chow: Chow = {
      lastUpdated: (new Date()).toISOString().split('.')[0]
    };
    if (food !== "") {
      chow.food = food;
    }
    if (meetLocation !== "") {
      chow.meetLocation = meetLocation;
    }
    if (meetTime !== "") {
      chow.meetTime = meetTime;
    }
    if (notes !== "") {
      chow.notes = notes;
    }
    this.chowService.editChow(this.chow.id, chow)
      .subscribe(updatedChow => {
        window.location.href = '/chow-list';
      });
  }
}
