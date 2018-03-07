import {inject, TestBed} from '@angular/core/testing';

import {ChowService} from './chow.service';

describe('ChowService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [ChowService]
    });
  });

  it('should be created', inject([ChowService], (service: ChowService) => {
    expect(service).toBeTruthy();
  }));
});
