import {Component, Input, OnInit} from '@angular/core';
import {Chow} from "../chow";

@Component({
  selector: 'app-chow-detail',
  templateUrl: './chow-detail.component.html',
  styleUrls: ['./chow-detail.component.css']
})
export class ChowDetailComponent implements OnInit {
  @Input() chow: Chow;

  constructor() {
  }

  ngOnInit() {
  }
}
