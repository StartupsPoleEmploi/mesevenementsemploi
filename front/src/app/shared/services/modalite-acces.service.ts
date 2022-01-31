import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';

@Injectable({
    providedIn: 'root'
})
export class ModaliteAccesService {

    private pathModaliteAcces = '/modaliteAcces';

    constructor(private httpClient: HttpClient) {
    }

    public getAllModaliteAccessByEvent(eventId: number | undefined): Observable<any> {
        return this.httpClient.get(this.pathModaliteAcces + '/all/evenement/' + eventId);
    }

    public getAllModaliteAccess(): Observable<any> {
        return this.httpClient.get(this.pathModaliteAcces + '/all');
    }
}
