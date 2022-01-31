import {Injectable} from '@angular/core';
import {Observable, Subject} from 'rxjs';
import {CookiesPeactionsService} from './cookies-peactions.service';
import {ICandidat} from '../../model/ICandidat';


@Injectable({providedIn: 'root'})
export class CandidatConnectedService {

  public candidatConnected: ICandidat | undefined;
  isStatutCandidatChangedSubject: Subject<boolean>;
  statutCandidatChanged: Observable<boolean>;

  constructor(
      private cookiesPeactionsService: CookiesPeactionsService
  ) {
    this.isStatutCandidatChangedSubject = new Subject<boolean>();
    this.statutCandidatChanged = this.isStatutCandidatChangedSubject.asObservable();
  }

  public getCandidatConnected(): ICandidat {
    if (!this.candidatConnected) {
      this.candidatConnected = this.cookiesPeactionsService.getCandidatConnected();
    }
    return this.candidatConnected;
  }

  public saveCandidatConnected(candidat: ICandidat): void {
    this.candidatConnected = candidat;
    this.cookiesPeactionsService.storeCandidatConnecte(candidat);
    this.isStatutCandidatChangedSubject.next(true);
  }

  public emitCandidatConnectedLogoutEvent(): void {
    this.isStatutCandidatChangedSubject.next(false);
  }

  public isLoggedIn(): boolean {
    return this.cookiesPeactionsService.getCandidatConnected() !== null;
  }
}
