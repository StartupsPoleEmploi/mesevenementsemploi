import {ErrorHandler, Injectable} from '@angular/core';
import {ApiService} from './api.service';
import {TokenStorageService} from './token-storage.service';
import {from, Observable} from 'rxjs';
import {MatSnackBar} from "@angular/material/snack-bar";

@Injectable({
  providedIn: 'root'
})
export class AgencyService extends ApiService {

  private pathAgence = '/etablissement';
  private pathAgenceByCode = '/';

  constructor(errorHandler: ErrorHandler, tokenStorage: TokenStorageService, snackBar: MatSnackBar) {
    super(errorHandler, tokenStorage, snackBar);
  }

  public getAllAgencies(): Observable<any> {
    try {
      const promise = this.getApi(this.pathAgence + '/agence/all/');
      const agencies = from(promise);
      return agencies;
    } catch (error) {
      return error;
    }
  }


  public getAgencyByCode(codeAgence: string): Observable<any> {
    try {
      const promise = this.getByCodeFromApi(this.pathAgenceByCode, codeAgence);
      const states = from(promise);
      return states;
    } catch (error) {
      return error;
    }
  }
}
