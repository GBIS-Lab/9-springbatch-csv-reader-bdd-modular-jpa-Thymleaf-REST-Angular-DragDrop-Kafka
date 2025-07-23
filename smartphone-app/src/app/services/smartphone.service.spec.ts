import { TestBed } from '@angular/core/testing';

import { SmartphoneService } from './smartphone.service';

describe('SmartphoneService', () => {
  let service: SmartphoneService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SmartphoneService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
