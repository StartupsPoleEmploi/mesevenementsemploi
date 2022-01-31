import {ErrorHandler, Injectable} from '@angular/core';
import {ApiService} from './api.service';
import {TokenStorageService} from './token-storage.service';
import {from, Observable} from 'rxjs';
import {MatSnackBar} from '@angular/material/snack-bar';

@Injectable({
  providedIn: 'root'
})
export class NafService extends ApiService {

  constructor(errorHandler: ErrorHandler, tokenStorage: TokenStorageService, snackBar: MatSnackBar) {
    super(errorHandler, tokenStorage, snackBar);
  }

  private nafPath = '/naf/all';

  public getNafList(): Observable<any> {
    try {
      const promise = this.getApi(this.nafPath);
      return from(promise);
    } catch (error) {
      return error;
    }
  }
}
