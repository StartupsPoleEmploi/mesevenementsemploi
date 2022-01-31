import {TestBed} from '@angular/core/testing';

import {ModalityAccessService} from './modality-access.service';

describe('ModalityAccessService', () => {
  let service: ModalityAccessService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ModalityAccessService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
