import {Inject, Injectable} from '@angular/core';
import {environment} from '../../../environments/environment';
import {PeConnectPayload} from '../../model/PeConnectPayload';
import {SessionStorageService} from 'ngx-webstorage';
import {DOCUMENT} from '@angular/common';
import {KeysStorageEnum} from '../../model/keys-storage.enum';
import {RoutesEnum} from '../../model/routes.enum';
import {ActivatedRoute, Router} from '@angular/router';
import {Individu} from '../../model/individu';
import {HttpClient} from '@angular/common/http';
import {CookieService} from 'ngx-cookie-service';
import {CookiesPeactionsService} from './cookies-peactions.service';
import {CandidatConnectedService} from './candidat-connected.service';
import {ICandidat} from '../../model/ICandidat';

@Injectable({
  providedIn: 'root'
})
export class PeConnectService {

  // tslint:disable-next-line:variable-name
  private _peConnectPayload!: PeConnectPayload;
  private pathDemandeurEmploiService: string | undefined;

  constructor(
      private router: Router,
      private http: HttpClient,
      private sessionStorageService: SessionStorageService,
      private activatedRoute: ActivatedRoute,
      private cookieService: CookieService,
      private cookiesPeactionsService: CookiesPeactionsService,
      private candidatConnectedService: CandidatConnectedService,
      @Inject(DOCUMENT) private document: Document,
  ) {
  }

  public login(redirectURI: string): void {
    this.createPeConnectPayload(redirectURI);
    this.document.location.href = this.getPoleEmploiIdentityServerConnexionURI();
  }

  public storePeConnectPayload(peConnectPayload: PeConnectPayload): void {
    localStorage.setItem(KeysStorageEnum.PE_CONNECT_PAYLOAD_STORAGE_SESSION_KEY, JSON.stringify(peConnectPayload));
  }

  private getPoleEmploiIdentityServerConnexionURI(): string {
    return `${environment.peconnectIdentityServerURL}connexion/oauth2/authorize?` +
        // `` ${environment.peconnectIdentityServerURL}connexion/XUI/?+
        'realm=%2Findividu&response_type=code' +
        `&client_id=${this._peConnectPayload.clientId}` +
        `&scope=${environment.peconnectScope}` +
        `&redirect_uri=${this._peConnectPayload.redirectURI}` +
        `&state=${this._peConnectPayload.state}` +
        `&nonce=${this._peConnectPayload.nonce}`;
  }

  private generateRandomValue(): string {
    const validChars = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
    let array = new Uint8Array(64);
    window.crypto.getRandomValues(array);
    array = array.map(x => validChars.charCodeAt(x % validChars.length));
    // @ts-ignore
    return String.fromCharCode.apply(null, array);
  }

  private createPeConnectPayload(redirectURI: string): void {
    this._peConnectPayload = new PeConnectPayload();
    this._peConnectPayload.clientId = environment.esdClientId;
    this._peConnectPayload.nonce = this.generateRandomValue();
    this._peConnectPayload.redirectURI = redirectURI;
    this._peConnectPayload.state = this.generateRandomValue();

    this.storePeConnectPayload(this._peConnectPayload);
  }

  public getPayloadPeConnect(): PeConnectPayload {
    return JSON.parse(localStorage.getItem(KeysStorageEnum.PE_CONNECT_PAYLOAD_STORAGE_SESSION_KEY) as string);
  }

  public getIndividuConnected(): Individu {
    let individu = null;
    const peConnectAuthorisationString = this.cookieService.get(KeysStorageEnum.PE_CONNECT_INDIVIDU);
    if (peConnectAuthorisationString) {
      individu = JSON.parse(peConnectAuthorisationString);
    }
    return individu;
  }

  public authentifier(peConnectPayload: PeConnectPayload): Promise<Individu> {
    return this.http.post<Individu>(`${this.pathDemandeurEmploiService}authentifier`, peConnectPayload).toPromise();
  }

  public logout(): void {
    const eventId = localStorage.getItem('eventId');
    this.sessionStorageService.clear();
 //   window.localStorage.clear();
    const candidatConnected = this.cookiesPeactionsService.getCandidatConnected();
    const poleEmploiIdentityServerDeconnexionURI = this.getPoleEmploiIdentityServerDeconnexionURI(candidatConnected);
    this.cookiesPeactionsService.clear();
    if (poleEmploiIdentityServerDeconnexionURI !== null) {
      this.document.location.assign(poleEmploiIdentityServerDeconnexionURI);
    } else {
      this.router.navigate([RoutesEnum.EVENTS]);
      this.candidatConnectedService.emitCandidatConnectedLogoutEvent();
    }
  }

  private getPoleEmploiIdentityServerDeconnexionURI(candidatConnected: ICandidat): string {
    let poleEmploiIdentityServerDeconnexionURI = '';
    if (candidatConnected && candidatConnected.peConnectAuthorization && candidatConnected.peConnectAuthorization?.idToken) {
      poleEmploiIdentityServerDeconnexionURI = `${environment.peconnectIdentityServerURL}compte/deconnexion?` +
          `&id_token_hint=${candidatConnected.peConnectAuthorization?.idToken}` +
          `&redirect_uri=${environment.urlAcces}${RoutesEnum.EVENTS}`;
    }
    return poleEmploiIdentityServerDeconnexionURI;
  }
}
