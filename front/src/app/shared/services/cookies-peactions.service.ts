import {Injectable} from '@angular/core';
import {CookieService} from 'ngx-cookie-service';
import {KeysStorageEnum} from '../../model/keys-storage.enum';
import {ICandidat} from '../../model/ICandidat';

@Injectable({providedIn: 'root'})
export class CookiesPeactionsService {

  constructor(
      private cookieService: CookieService
  ) {

  }

  public storeCandidatConnecte(candidat: ICandidat): void {
    const dateTokenExpired = this.getDateCookieExpire(candidat.peConnectAuthorization?.expireIn);
    this.cookieService.set(
        KeysStorageEnum.PE_CONNECT_INDIVIDU, JSON.stringify(candidat),
        {expires: dateTokenExpired, path: '/', secure: true}
    );
  }

  public getCandidatConnected(): ICandidat {
    let candidat = null;
    const peConnectAuthorisationString = this.cookieService.get(KeysStorageEnum.PE_CONNECT_INDIVIDU);
    if (peConnectAuthorisationString) {
      candidat = JSON.parse(peConnectAuthorisationString);
    }
    return candidat;
  }

  public clear(): void {
    this.cookieService.delete(KeysStorageEnum.PE_CONNECT_INDIVIDU);
  }

  private getDateCookieExpire(tokenPeConnectExpireIn: number | undefined): Date | undefined {

    // on enlève 3min pour refaire une demande avant l'expiration du token qui obligerait l'utilisateur a se réauthentifier.
    if (tokenPeConnectExpireIn) {

      const dureeStorageCookieEnSecond = tokenPeConnectExpireIn - 180;
      const dureeStorageCookieEnMin = dureeStorageCookieEnSecond / 60;

      // création de la date d'expiration du cookie
      const dateNow = new Date();
      dateNow.setMinutes(dateNow.getMinutes() + dureeStorageCookieEnMin);

      return dateNow;
    }
    return undefined;
  }
}
