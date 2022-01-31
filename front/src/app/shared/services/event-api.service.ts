import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class EventApiService {

  private pathEvent = '/evenement';

  constructor(private httpClient: HttpClient) {

  }

  public getAllEvents(pageNo: number, codeDept: string): Observable<any> {
    return this.httpClient.get(this.pathEvent + '/all/' + codeDept + '/' + pageNo);
  }

  public getEventById(id: number | undefined): Observable<any> {
    return this.httpClient.get(this.pathEvent + '/detail/' + id);
  }

  public getTotalEventNumber(codeDept: any): Observable<any> {
    return this.httpClient.get(this.pathEvent + '/count/all/' + codeDept );
  }

}
