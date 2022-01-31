import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';

import {BsModalRef} from 'ngx-bootstrap/modal';
import {RoutesEnum} from '../../../model/routes.enum';
import {PeConnectService} from '../../services/pe-connect.service';
import {SessionStoragePeactionsService} from '../../services/session-storage-peactions.service';
import {CandidatConnectedService} from '../../services/candidat-connected.service';
import {CookiesPeactionsService} from '../../services/cookies-peactions.service';
import {environment} from '../../../../environments/environment';

@Component({
  selector: 'app-modal-session-expired',
  templateUrl: './modal-session-expired.component.html',
  styleUrls: ['./modal-session-expired.component.scss']
})
export class ModalSessionExpiredComponent implements OnInit {
  constructor(
      private bsModalRef: BsModalRef,
      private cookiesPeactionsService: CookiesPeactionsService,
      private candidatConnectedService: CandidatConnectedService,
      private peConnectService: PeConnectService,
      private sessionStoragePeactionsService: SessionStoragePeactionsService,
      private router: Router
  ) {

  }

  ngOnInit(): void {
  }

  public onClickButtonClosePopup(): void {

    this.bsModalRef.hide();
    this.candidatConnectedService.emitCandidatConnectedLogoutEvent();

    // au cas ou la session ne serait pas encore expirée, cela ne devrait pas arriver :)
    this.peConnectService.logout();

    this.sessionStoragePeactionsService.clear();
    this.cookiesPeactionsService.clear();
    this.router.navigate([RoutesEnum.EVENTS]);
  }

  public onClickButtonSeConnecterAvecPE(): void {
    this.peConnectService.login(`${environment.urlAcces}${RoutesEnum.EVENTS}`);
  }

}
