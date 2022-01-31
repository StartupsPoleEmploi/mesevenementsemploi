import {ErrorHandler, Injectable} from '@angular/core';
import {ApiService} from './api.service';
import axios from 'axios';
import {environment} from '../../../environments/environment';
import {TokenStorageService} from './token-storage.service';
import {from, Observable} from 'rxjs';
import {MatSnackBar} from '@angular/material/snack-bar';

@Injectable({
  providedIn: 'root'
})
export class EventApiService extends ApiService {

  private postOptions = '/evenement';

  constructor(errorHandler: ErrorHandler, private tokenStorageService: TokenStorageService, snackBar: MatSnackBar) {
    super(errorHandler, tokenStorageService, snackBar);
  }

  public getAllEventsSorted(page: number): Observable<any> {
    const baseUrl = '/evenement/all/back/sorted' + `?page=${page}&size=10`;
    try {
      const promise = this.getApi(baseUrl);
      return from(promise);
    } catch (error) {
      return error;
    }
  }

  public getAllEventsSortedByAgency(codeAgence: string | null, page: number): Observable<any> {
    const baseUrl = '/evenement/all/back/sorted/agence' + '/' + codeAgence + `?page=${page}&size=10`;
    try {
      const promise = this.getApi(baseUrl);
      return from(promise);
    } catch (error) {
      return error;
    }
  }

  public deleteEventById(id: number | undefined): void {
    const baseUrl = environment.peactonsDomainePath + '/evenement/';
    const token = this.tokenStorageService.getToken();
    axios.delete(baseUrl + id, {headers: {Authorization: token}})
      .then(res => {
      }).catch((err) => {
      return err;
    });
  }

  public getEventById(id: number | undefined): Observable<any> {
    const baseUrl = '/evenement/detail/' + id;
    try {
      const promise = this.getApi(baseUrl);
      const event = from(promise);
      return event;
    } catch (error) {
      return error;
    }
  }

  public createEvent(payLoad: {
    ville: any;
    modeAcceesList: ({
      modaliteAcces: { id: number };
      nombrePlace: any;
      nombrePresent: number;
      nombreInscrit: number })[];
    titre: any;
    offres: any;
    romeId: any;
    description: any;
    timeZone: string;
    codePostal: any;
    heureFin: any;
    intervenants: any;
    tags: any[];
    estApublier: boolean;
    etatId: number;
    nafCode: any;
    adresse: any;
    dateEvenement: string | null;
    heureDebut: any;
    deroulement: any;
    prerequis: any
  }): Observable<any> {
      const promise = this.postApi(this.postOptions, payLoad);
      const result = from(promise);
      result.subscribe(data => {
      })
      return result;
  }

  public updateEvent(event: any, id: any): Observable<any> {
    const baseUrl = '/evenement';
    const promise = this.putApi(baseUrl + '/' + id, event);
    const result = from(promise);
    return result;
  }

  public getTotalEventNumber(): Observable<any> {
    const baseUrl = '/evenement/count/all';
    try {
      const promise = this.getApi(baseUrl);
      const count = from(promise);
      return count;
    } catch (error) {
      return error;
    }
  }

  public getTotalEventByAgency(agencyId: string | null): Observable<any> {
    const baseUrl = `/evenement/count/agence/${agencyId}`;
    try {
      const promise = this.getApi(baseUrl);
      const countByAgency = from(promise);
      return countByAgency;
    } catch (error) {
      return error;
    }
  }
}
