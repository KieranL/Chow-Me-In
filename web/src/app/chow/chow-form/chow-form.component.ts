import {Router} from '@angular/router';
import {Component, OnInit} from '@angular/core';
import {ChowService} from '../chow.service';
import {UserService} from '../../auth/user.service';
import {Chow} from '../chow';

@Component({
  selector: 'app-chow-form',
  templateUrl: './chow-form.component.html',
  styleUrls: ['./chow-form.component.css']
})
export class ChowFormComponent implements OnInit {
  constructor(private router: Router, private chowService: ChowService, private userService: UserService) {
  }

  ngOnInit() {
  }

  add(food: string, meetLocation: string, meetTime: string, notes: string): void {
    const _this = this;

    var token = this.userService.getAuthTokenFromStorage();

    this.userService.getUserFromToken(token).subscribe(
      data => {
        if(data['success'] == true) {
          let newChow: Chow = {
            posterUser: _this.userService.getUsernameFromObject(data),
            posterName: _this.userService.getUsersNameFromObject(data),
            posterEmail: _this.userService.getUsersEmailFromObject(data),
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


          _this.chowService.addChow(newChow).subscribe(
            chow => {
              _this.router.navigate(['chow-list']);
            });
        }
      },
      err => {
        console.error(err);
      }
    );


  }
}
