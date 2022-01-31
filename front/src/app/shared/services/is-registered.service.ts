import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class IsRegisteredService {

  private candidateRegistration = new BehaviorSubject(false);
  isCandidateRegistered = this.candidateRegistration.asObservable();

  constructor() {
  }

  public changeRegistrationStatus(status: boolean) {
    this.candidateRegistration.next(status);
  }
}
