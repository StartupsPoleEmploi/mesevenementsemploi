import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class OfferService {

  private url = '/esd/offre/';

  constructor(private httpClient: HttpClient) {
  }

  public getOfferById(offerId: string | undefined): Observable<any> {
    return this.httpClient.get(this.url + offerId);
  }
}
