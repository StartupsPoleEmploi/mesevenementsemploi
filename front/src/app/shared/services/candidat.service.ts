import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {Individu} from '../../model/individu';
import {ActivatedRoute, Router} from '@angular/router';
import {CandidatConnectedService} from './candidat-connected.service';
import {MatSnackBar} from '@angular/material/snack-bar';

@Injectable({
    providedIn: 'root'
})
export class CandidatService {
    public path = '/candidat';

    constructor(private activatedRoute: ActivatedRoute,
                private httpClient: HttpClient, private snackBar: MatSnackBar, private router: Router,
                private candidatConnectedService: CandidatConnectedService) {
    }

    public createCandidatEvenement(evenementId: number | undefined, modaliteId: number | undefined, prerequisValider: boolean): Observable<any> {
        const url = '/evenement';
        const candidat = this.candidatConnectedService.getCandidatConnected();
        if (candidat !== undefined && candidat !== null) {
            candidat.modaliteAccesId = modaliteId;
            candidat.prerequisValider = prerequisValider;
            candidat.statutInscription = 0;
        }
        const response = this.httpClient.post(this.path + url + '/' + evenementId, candidat);
        // console.log('response inscription', response);
        return response;
    }

    public getAllCandidatByEvent(id: number | undefined, avecPrerequisValider: boolean): Observable<any> {
        const baseUrl = '/all/evenement';
        return this.httpClient.get(this.path + baseUrl + '/' + id + '?avecPrerequisValider=' + avecPrerequisValider);
    }

    public authentifier(peConnectPayload: any): Observable<Individu> {
        const url = '/authentifier';
        return this.httpClient.post<Individu>(this.path + url, peConnectPayload);
    }

    public updateCandidatEvenement(evenementId: number | undefined, statutInscription: number): Observable<any> {
        const url = '/evenement';
        const candidat = this.candidatConnectedService.getCandidatConnected();
        if (candidat !== undefined && candidat !== null && candidat.statutInscription !== undefined) {
            candidat.statutInscription = 3;
        }
        const response = this.httpClient.put(this.path + url + '/' + evenementId, candidat);
        // console.log('response update inscription', response);
        return response;
    }

    public isCandidatInscrit(evenementId: number | undefined, candidatId: number | undefined): Observable<any> {
        const url = '/inscrit';
        let params = new HttpParams();
        const headers = new HttpHeaders().set('Content-Type', 'application/json');
        // @ts-ignore
        params = params.append('evenementId', evenementId);
        // @ts-ignore
        params = params.append('candidatId', candidatId);

        return this.httpClient.get(this.path + url + '/' + evenementId + '/' + candidatId, {headers});
    }
}
