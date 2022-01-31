import {ErrorHandler, Injectable} from '@angular/core';
import {ApiService} from './api.service';
import {TokenStorageService} from './token-storage.service';
import {from, Observable} from 'rxjs';
import {MatSnackBar} from '@angular/material/snack-bar';

@Injectable({
  providedIn: 'root'
})
export class ModalityAccessService extends ApiService {

  private options: string = '/modaliteAcces/all';

  constructor(errorHandler: ErrorHandler, tokenStorage: TokenStorageService, snackBar: MatSnackBar) {
    super(errorHandler, tokenStorage, snackBar);
  }

  public getAllModalityAccess(): Observable<any> {
    try {
      const promise = this.getApi(this.options);
      const modalitiesAccess = from(promise);
      return modalitiesAccess;
    } catch (error) {
      return error;
    }
  }

  public getAllModaliteAccessByEventId(eventId: number | undefined): Observable<any> {
    const baseUrl = '/modaliteAcces/all/evenement';
    try {
      const promise = this.getApi(baseUrl + '/' + eventId);
      const candidats = from(promise);
      return candidats;
    } catch (error) {
      return error;
    }
  }
}
