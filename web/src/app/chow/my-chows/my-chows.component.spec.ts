import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MyChowsComponent } from './my-chows.component';

describe('MyChowsComponent', () => {
  let component: MyChowsComponent;
  let fixture: ComponentFixture<MyChowsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MyChowsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MyChowsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
