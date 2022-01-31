import {TestBed} from '@angular/core/testing';

import {ModaliteAccesService} from './modalite-acces.service';

describe('ModaliteAccesService', () => {
  let service: ModaliteAccesService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ModaliteAccesService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
