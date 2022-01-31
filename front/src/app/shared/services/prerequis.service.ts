import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PrerequisService {

  private url = '/prerequis/all';

  constructor(private httpClient: HttpClient) {
  }

  public getAllPrerequisitesById(prerequisId: number): Observable<any> {
    return this.httpClient.get(this.url + '/' + prerequisId);
  }
}
