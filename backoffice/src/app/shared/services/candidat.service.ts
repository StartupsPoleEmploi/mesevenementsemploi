import {ErrorHandler, Injectable} from '@angular/core';
import {TokenStorageService} from './token-storage.service';
import {from, Observable} from 'rxjs';
import {ApiService} from './api.service';
import {ICandidat} from '../../model/ICandidat';
import {MatSnackBar} from '@angular/material/snack-bar';
import {Router} from '@angular/router';
import {environment} from '../../../environments/environment';
import axios from 'axios';

@Injectable({
  providedIn: 'root'
})
export class CandidatService extends ApiService {

  constructor(errorHandler: ErrorHandler, private router: Router,
              private tokenStorageService: TokenStorageService, snackBar: MatSnackBar) {
    super(errorHandler, tokenStorageService, snackBar);
  }

  public getAllCandidatByEvent(id: number | undefined, avecPrerequisValider: boolean): Observable<any> {
    const baseUrl = '/candidat/all/evenement';
    try {
      const promise = this.getApi(baseUrl + '/' + id + '?avecPrerequisValider=' + avecPrerequisValider);
      return from(promise);
    } catch (error) {
      return error;
    }
  }

  public presenceCandidatEvent(idEvent: number | undefined, candidat: ICandidat | undefined): Observable<any> {
    const baseUrl = '/candidat/presence/evenement';
    try {
      // @ts-ignore
      const promise = this.postApi(baseUrl + '?evenementId=' + idEvent, candidat);
      return from(promise);
    } catch (error) {
      return error;
    }
  }

  inscrireCandidat(candidat: {}, eventId: number | undefined): Observable<any> {
    const baseUrl = '/candidat/evenement' + '?evenementId=' + eventId;
    try {
      const promise = this.postCandidat(baseUrl, candidat);
      return from(promise);
    } catch (error) {
      return error;
    }
  }

  public deleteCandidatById(eventId: number | undefined, candidatId: number | undefined): void {
    const baseUrl = environment.peactonsDomainePath + '/candidat/evenement' + '?evenementId=' + eventId + '&candidatId=' + candidatId;
    axios.delete(baseUrl, {
      headers: {
        Accept: '*/*'
      }
    })
      .then(res => {
        return res;
      }).catch((err) => {
      return err;
    });
  }
}
