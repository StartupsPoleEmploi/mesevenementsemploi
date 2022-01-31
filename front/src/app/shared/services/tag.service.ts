import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TagService {

  private url = '/tag/tags/type';

  constructor(private httpClient: HttpClient) {
  }

  public getAllTagsByType(typeId: number): Observable<any> {
    return this.httpClient.get(this.url + '/' + typeId);
  }
}
