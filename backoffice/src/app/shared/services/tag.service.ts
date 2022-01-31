import {ErrorHandler, Injectable} from '@angular/core';
import {ApiService} from './api.service';
import {TokenStorageService} from './token-storage.service';
import {from, Observable} from 'rxjs';
import {MatSnackBar} from "@angular/material/snack-bar";

@Injectable({
  providedIn: 'root'
})
export class TagService extends ApiService {

  private options = '/tag/type/all';
  private optionsTag = '/tag';
  private allTagsByTypePath = '/tag/tags/type/';

  constructor(errorHandler: ErrorHandler, tokenStorage: TokenStorageService, snackBar: MatSnackBar) {
    super(errorHandler, tokenStorage, snackBar);
  }

  public getAllTypesTags(): Observable<any> {
    try {
      const promise = this.getApi(this.options);
      const tags = from(promise);
      return tags;
    } catch (error) {
      return error;
    }
  }
  public getAllTagsByType(typeId: number): Observable<any> {
    try {
      const promise = this.getApi(this.allTagsByTypePath + typeId);
      const tags = from(promise);
      return tags;
    } catch (error) {
      return error;
    }
  }

  public getTagById(idTag: number): Observable<any> {
    try {
      const promise = this.getByIdFromApi(this.optionsTag, idTag);
      const states = from(promise);
      return states;
    } catch (error) {
      return error;
    }
  }

}
