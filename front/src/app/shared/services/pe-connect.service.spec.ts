import {TestBed} from '@angular/core/testing';

import {PeConnectService} from './pe-connect.service';

describe('PeConnectService', () => {
  let service: PeConnectService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PeConnectService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
