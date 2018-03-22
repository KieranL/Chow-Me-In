import {Router, ActivatedRoute} from '@angular/router';
import {Component, Input, OnInit} from '@angular/core';
import {ChowService} from '../chow.service';
import {UserService} from '../../auth/user.service';
import {Chow} from '../chow';
import {Location} from '@angular/common';
import { Ng4LoadingSpinnerService } from 'ng4-loading-spinner';

@Component({
  selector: 'app-chow-form',
  templateUrl: './chow-form.component.html',
  styleUrls: ['./chow-form.component.css']
})
export class ChowFormComponent implements OnInit {
  @Input() chow: Chow;

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private location: Location,
    private chowService: ChowService,
    private userService: UserService,
    private spinnerService: Ng4LoadingSpinnerService
  ) {
  }

  ngOnInit() {
    const id = +this.route.snapshot.paramMap.get('id');
    if (id) {
      this.spinnerService.show();

      this.chowService.getChowById(id).subscribe((chow) => {
        this.spinnerService.hide();
        this.chow = chow;
      });
    }
  }

  add(food: string, meetLocation: string, meetTime: string, notes: string): void {
    const _this = this;

    let token = this.userService.getAuthTokenFromStorage();

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

  save(food: string, meetLocation: string, meetTime: string, notes: string): void {
    const _this = this;

    let chow: Chow = {
      posterUser: this.chow.posterUser,
      posterName: this.chow.posterName,
      posterEmail: this.chow.posterEmail,
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
        _this.router.navigate(['chow-list']);
      });
  }

  goBack(): void {
    this.location.back();
  }
}
