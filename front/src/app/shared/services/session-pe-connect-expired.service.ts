import {Injectable} from '@angular/core';
import {Subscription} from 'rxjs';
import {Router} from '@angular/router';
import {CookiesPeactionsService} from './cookies-peactions.service';
import {SessionStoragePeactionsService} from './session-storage-peactions.service';
import {BnNgIdleService} from 'bn-ng-idle';
import {RoutesEnum} from '../../model/routes.enum';
import {DialogConfirmationComponent} from '../component/dialog-confirmation/dialog-confirmation.component';
import {MatDialog} from '@angular/material/dialog';


@Injectable({providedIn: 'root'})
export class SessionPeConnectExpiredService {

  subscriptionStartWatchingObservable: Subscription | undefined;

  constructor(
      private bnNgIdleService: BnNgIdleService,
      private dialog: MatDialog,
      private cookiesPeactionsService: CookiesPeactionsService,
      private sessionStoragePeactionsService: SessionStoragePeactionsService,
      private router: Router
  ) {

  }

  public startCheckUserInactivity(sessionExpireIn: number | undefined): void {
    // appelé quand la session utilisateur PE Connect a expirée
    if (sessionExpireIn) {
      this.subscriptionStartWatchingObservable = this.bnNgIdleService.startWatching(sessionExpireIn).subscribe((isTimedOut: boolean) => {
        if (isTimedOut) {
          this.openModal();
          this.bnNgIdleService.stopTimer();
        }
      });
    }
  }

  public expierationMadal(): void {
    const confirmDialog = this.dialog.open(DialogConfirmationComponent, {
      data: {
        message: 'Votre session a expiré, veuillez vous reconnecter.',
        buttonText: {
          ok: 'Se connecter avec pole emploi',
          cancel: ''
        }
      }
    });
    confirmDialog.afterClosed().subscribe(result => {
      if (result === true) {
      }
    }, err => {
      // console.log('HTTP Error', err);
    });
  }

  public openModal(): void {
    this.expierationMadal();
  }


  public isIndividuBackAfterSessionExpired(): boolean {
    const pathRouteActivated = this.sessionStoragePeactionsService.getPathRouteActivatedByUser();
    return !!pathRouteActivated;
  }

  public navigateToRouteActivated(): void {
    if (this.isMemeIndividuReconnecteAfterSessionPeConnectExpired()) {
      this.traiterReconnexionMemeIndividu();
    } else {
      this.traiterReconnexionIndividuDifferent();
    }
  }

  private isMemeIndividuReconnecteAfterSessionPeConnectExpired(): boolean {
      let isMemeIndividu = false;
      const deConnecte = this.sessionStoragePeactionsService.getCandidatConnected();
      const individuConnecte = this.cookiesPeactionsService.getCandidatConnected();
      if (deConnecte && individuConnecte) {
          isMemeIndividu = deConnecte.identifiantCrypter === individuConnecte.identifiantCrypter;
      }
      return isMemeIndividu;
  }

  private traiterReconnexionMemeIndividu(): void {
    const pathRouteActivated = this.sessionStoragePeactionsService.getPathRouteActivatedByUser();
    this.router.navigate([RoutesEnum.EVENTS]);
    this.sessionStoragePeactionsService.clearPathRouteActivatedByUser();
  }

  private traiterReconnexionIndividuDifferent(): void {
    this.sessionStoragePeactionsService.clear();
    this.router.navigate([RoutesEnum.EVENTS]);
  }
}
