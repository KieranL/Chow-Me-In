import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ChowFormComponent } from './chow-form.component';

describe('ChowFormComponent', () => {
  let component: ChowFormComponent;
  let fixture: ComponentFixture<ChowFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ChowFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ChowFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
