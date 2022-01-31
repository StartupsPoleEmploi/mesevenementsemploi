import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DepartementService {
  private url = '/departement';
  private urlEvent = '/evenement';

  constructor(private httpClient: HttpClient) {
  }

  public getAllDepartements(): Observable<any> {
    return this.httpClient.get(this.url + '/all');
  }

  public getAllEvenementsByDepartement(codeDepartement: string | undefined): Observable<any> {
    return this.httpClient.get(this.urlEvent + '/all/departement/' + codeDepartement);
  }
}
