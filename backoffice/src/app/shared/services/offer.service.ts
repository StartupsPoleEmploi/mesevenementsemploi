import {ErrorHandler, Injectable} from '@angular/core';
import {from, Observable} from "rxjs";
import {TokenStorageService} from './token-storage.service';
import {MatSnackBar} from '@angular/material/snack-bar';
import {ApiService} from './api.service';
import {IOffer} from '../../model/IOffer';

@Injectable({
  providedIn: 'root'
})
export class OfferService extends ApiService {

  private offerUrl = '/esd/offre';
  offer: any = {};

  constructor(errorHandler: ErrorHandler, tokenStorage: TokenStorageService, snackBar: MatSnackBar) {
    super(errorHandler, tokenStorage, snackBar);
  }

  public getOfferById(offerId: string): Observable<IOffer> {
    return from(this.getByIdFromApi(this.offerUrl, offerId));
  }
}
