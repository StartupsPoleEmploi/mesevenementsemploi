import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot} from '@angular/router';
import {SessionStorageService} from 'ngx-webstorage';
import {KeysStorageEnum} from '../../model/keys-storage.enum';
import {PeConnectPayload} from '../../model/PeConnectPayload';
import {ICandidat} from "../../model/ICandidat";

@Injectable({providedIn: 'root'})
export class SessionStoragePeactionsService {


  constructor(
      private sessionStorageService: SessionStorageService
  ) {

  }

  public clear(): void {
    this.clearPathRouteActivatedByUser();
    this.sessionStorageService.clear(KeysStorageEnum.INDIVIDU_CONNECTE_STORAGE_SESSION_KEY);
    this.sessionStorageService.clear(KeysStorageEnum.PE_CONNECT_PAYLOAD_STORAGE_SESSION_KEY);
  }

  public clearMessageCandidat(): void {
    return this.sessionStorageService.clear(KeysStorageEnum.DEMANDEUR_EMPLOI_MESSAGE_NON_AUTORISE);
  }

  public clearPathRouteActivatedByUser(): void {
    return this.sessionStorageService.clear(KeysStorageEnum.DEMANDEUR_EMPLOI_CONNECTE_ROUTE_ACTIVATED_STORAGE_SESSION_KEY);
  }

  public getPathRouteActivatedByUser(): string {
    return this.sessionStorageService.retrieve(KeysStorageEnum.DEMANDEUR_EMPLOI_CONNECTE_ROUTE_ACTIVATED_STORAGE_SESSION_KEY);
  }

  public getPayloadPeConnect(): PeConnectPayload {
    return this.sessionStorageService.retrieve(KeysStorageEnum.PE_CONNECT_PAYLOAD_STORAGE_SESSION_KEY);
  }


  public storeRouteActivatedByUser(route: ActivatedRouteSnapshot | null): void {
    if (route) {
      this.sessionStorageService.store(KeysStorageEnum.DEMANDEUR_EMPLOI_CONNECTE_ROUTE_ACTIVATED_STORAGE_SESSION_KEY,
          route.routeConfig?.path);
    }
  }

  public storePeConnectPayload(peConnectPayload: PeConnectPayload): void {
    this.sessionStorageService.store(KeysStorageEnum.PE_CONNECT_PAYLOAD_STORAGE_SESSION_KEY, peConnectPayload);
  }

  public storeCandidatConnecte(demandeurEmploiConnecte: ICandidat): void {
    this.sessionStorageService.store(KeysStorageEnum.INDIVIDU_CONNECTE_STORAGE_SESSION_KEY, demandeurEmploiConnecte);
  }

  public getCandidatConnected(): ICandidat {
    return this.sessionStorageService.retrieve(KeysStorageEnum.INDIVIDU_CONNECTE_STORAGE_SESSION_KEY);
  }
}
