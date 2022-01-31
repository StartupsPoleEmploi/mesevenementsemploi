import { TestBed } from '@angular/core/testing';

import { IsRegisteredService } from './is-registered.service';

describe('IsRegisteredService', () => {
  let service: IsRegisteredService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(IsRegisteredService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
