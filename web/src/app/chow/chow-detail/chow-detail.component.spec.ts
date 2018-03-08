import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {ChowDetailComponent} from './chow-detail.component';

describe('ChowDetailComponent', () => {
  let component: ChowDetailComponent;
  let fixture: ComponentFixture<ChowDetailComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ChowDetailComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ChowDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
