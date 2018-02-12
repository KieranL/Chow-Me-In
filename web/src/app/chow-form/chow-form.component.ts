import { Component, OnInit } from '@angular/core';
import {ChowService} from '../chow.service';
import {Chow} from '../chow';

@Component({
  selector: 'app-chow-form',
  templateUrl: './chow-form.component.html',
  styleUrls: ['./chow-form.component.css']
})
export class ChowFormComponent implements OnInit {

  constructor(private chowService: ChowService) { }

  ngOnInit() {
  }

  add(food: string, meetLocation: string, meetTime: string, notes: string): void {
    const newChow = {
      id: null,
      food: food,
      meetLocation: meetLocation,
      meetTime: meetTime,
      lastUpdated: Date.now().toString(), // TODO: is lastUpdated determined on the server side or client side?
      notes: notes,
    };
    this.chowService.addChow(newChow as Chow)
      .subscribe(chow => {
        alert('Successfully created new chow!');
        window.location.href = '/chow-list';
      });
  }

}
