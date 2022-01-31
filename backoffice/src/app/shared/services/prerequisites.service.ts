import {ErrorHandler, Injectable} from '@angular/core';
import {ApiService} from './api.service';
import {TokenStorageService} from './token-storage.service';
import {from, Observable} from 'rxjs';
import {MatSnackBar} from "@angular/material/snack-bar";

@Injectable({
  providedIn: 'root'
})
export class PrerequisitesService extends ApiService {

  private prerequisitesPath = '/prerequis/all';
  private allPrerequisitesCategoriesOptions = '/prerequis/categorie/all';

  constructor(errorHandler: ErrorHandler, tokenStorage: TokenStorageService, snackBar: MatSnackBar) {
    super(errorHandler, tokenStorage, snackBar);
  }

  public getAllPrerequisites(): Observable<any> {
    try {
      const promise = this.getApi(this.prerequisitesPath);
      const prerequisites = from(promise);
      return prerequisites;
    } catch (error) {
      return error;
    }
  }

  public getAllPrerequisitesById(id: number): Observable<any> {
    try {
      const promise = this.getByIdFromApi(this.prerequisitesPath, id);
      const prerequisites = from(promise);
      return prerequisites;
    } catch (error) {
      return error;
    }
  }

  public getAllPrerequisitesCategories(): Observable<any> {
    try {
      const promise = this.getApi(this.allPrerequisitesCategoriesOptions);
      const prerequisites = from(promise);
      return prerequisites;
    } catch (error) {
      return error;
    }
  }
}
