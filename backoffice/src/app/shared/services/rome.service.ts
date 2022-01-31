import {ErrorHandler, Injectable} from '@angular/core';
import axios from 'axios';
import {BehaviorSubject, from, Observable} from 'rxjs';
import {ApiService} from './api.service';
import {TokenStorageService} from './token-storage.service';
import {IRome} from '../../model/IRome';
import {MatSnackBar} from '@angular/material/snack-bar';

@Injectable({
  providedIn: 'root'
})
export class RomeService extends ApiService {
  private static classInstance?: RomeService;
  private url = '/esd/romes';
  private romeListFromMap = new BehaviorSubject(Array<IRome>());
  romeListSelectedFromMap = this.romeListFromMap.asObservable();

  constructor(errorHandler: ErrorHandler, tokenStorage: TokenStorageService, snackBar: MatSnackBar) {
    super(errorHandler, tokenStorage, snackBar);
    this.recupererRomes();
  }

  public static getInstance(errorHandler: ErrorHandler, tokenStorage: TokenStorageService, snackBar: MatSnackBar): RomeService {
    if (!this.classInstance) {
      this.classInstance = new RomeService(errorHandler, tokenStorage, snackBar);
    }
    return this.classInstance;
  }

  public recupererRomes(): void {
    this.getAllRome().subscribe(datas => {
      datas.sort((a: any, b: any) => (a.code > b.code) ? 1 : ((b.code > a.code) ? -1 : 0));
      this.changeRomeList(datas);
    });
  }

  public changeRomeList(romeList: Array<IRome>): void {
    this.romeListFromMap.next(romeList);
  }

  public getAllRome(): Observable<any> {
    try {
      const promise = this.getApi(this.url);
      const romeList = from(promise);
      return romeList;
    } catch (error) {
      return error;
    }
  }

  public async getLibelleByCode(codeRome: string | null): Promise<void> {
    try {
      const result = await axios.get(this.url + '/' + codeRome + '?champs=libelle')
        .then(res => {
          return res.data;
        }).catch((err) => {
          return err;
        });
      return result;
    } catch (error) {
      console.error(error);
    }
  }
}
