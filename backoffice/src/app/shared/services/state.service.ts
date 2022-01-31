import {ErrorHandler, Injectable} from '@angular/core';
import {ApiService} from './api.service';
import {TokenStorageService} from './token-storage.service';
import {from, Observable} from 'rxjs';
import {MatSnackBar} from "@angular/material/snack-bar";

@Injectable({
  providedIn: 'root'
})
export class StateService extends ApiService {

  private options = '/etat/all';
  private optionsState = '/etat';

  constructor(errorHandler: ErrorHandler, tokenStorage: TokenStorageService, snackBar: MatSnackBar) {
    super(errorHandler, tokenStorage, snackBar);
  }

  public getAllStates(): Observable<any> {
    try {
      const promise = this.getApi(this.options);
      const states = from(promise);
      return states;
    } catch (error) {
      return error;
    }
  }

  public getStatesById(idState: number): Observable<any> {
    try {
      const promise = this.getByIdFromApi(this.optionsState, idState);
      const states = from(promise);
      return states;
    } catch (error) {
      return error;
    }
  }

}
