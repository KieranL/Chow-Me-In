import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {ChowListComponent} from './chow-list.component';

describe('ChowListComponent', () => {
  let component: ChowListComponent;
  let fixture: ComponentFixture<ChowListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ChowListComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ChowListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
