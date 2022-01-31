import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {IEvent} from '../../../model/IEvent';
import {ActivatedRoute, Router} from '@angular/router';
import {EventApiService} from '../../../shared/services/event-api.service';
import {OfferService} from '../../../shared/services/offer.service';
import {MatDialog} from '@angular/material/dialog';
import {DialogConfirmationComponent} from '../../../shared/component/dialog-confirmation/dialog-confirmation.component';
import {IOffer} from '../../../model/IOffer';
import {BreakpointObserver} from '@angular/cdk/layout';
import {Observable} from 'rxjs';
import {map} from 'rxjs/operators';
import {CandidatConnectedService} from '../../../shared/services/candidat-connected.service';
import {UrlService} from '../../../shared/services/url.service';
import {IModeAcees} from '../../../model/IModeAccess';
import {MatSnackBar} from '@angular/material/snack-bar';
import {ICandidat} from '../../../model/ICandidat';
import {CookiesPeactionsService} from '../../../shared/services/cookies-peactions.service';
import {CandidatService} from '../../../shared/services/candidat.service';
import {RoutesEnum} from '../../../model/routes.enum';
import {PeConnectService} from '../../../shared/services/pe-connect.service';
import {SessionPeConnectExpiredService} from '../../../shared/services/session-pe-connect-expired.service';
import {DialogAnnulationComponent} from '../../../shared/component/dialog-annulation/dialog-annulation.component';


@Component({
  selector: 'app-details-event',
  templateUrl: './details-event.component.html',
  styleUrls: ['./details-event.component.scss']
})
export class DetailsEventComponent implements OnInit, OnDestroy {

  @Input() event: IEvent | undefined;

  private originUrl = '';
  private previousUrl: Observable<string> = this.urlService.previousUrl$;
  private currentEvent: IEvent | undefined;
  private eventId: number | undefined;
  private candidatId: number | undefined;
  private sub: any;
  private benefices = 1;
  private publicCibles = 0;

  public offerTab = new Array<any>();
  public isSmallScreen: Observable<boolean>;
  public isWideScreen: Observable<boolean>;
  public nbPlaceEnLigne: number | undefined;
  public nbPlaceEnPhysique: number | undefined;
  public isFullyBooked: boolean | undefined;
  public afficherImageRecrutement = false;
  public afficherImageDecouverteMetier = false;
  public afficherImageFormation = false;
  public afficherImageTousMobilises = false;
  public beneficesList: any;
  public publicCiblesList: any;
  public publicObjectivesList: any;
  public remotePlace: IModeAcees[] | undefined;
  public onSitePlace: IModeAcees[] | undefined;
  public eventLocation: string | undefined;
  public shareUrl: string | undefined;
  public candidatConnected: ICandidat | undefined;
  public inscriptionAnnulee = false;
  public inscritID = 0;
  public annulationID = 3;
  public dejaInscrit = false;
  public statusInscrit = false;
  public StatutTab = new Array<any>();

  constructor(
      private route: ActivatedRoute,
      private eventService: EventApiService,
      private offerService: OfferService,
      private dialog: MatDialog,
      private router: Router,
      private breakpointObserver: BreakpointObserver,
      private urlService: UrlService,
      public cookiesPeactionsService: CookiesPeactionsService,
      public candidatConnectedService: CandidatConnectedService,
      private snackBar: MatSnackBar,
      private peConnectS: PeConnectService,
      private candidatSerivce: CandidatService,
      private sessionPeConnectExpiredS: SessionPeConnectExpiredService,
      private activatedRoute: ActivatedRoute
  ) {
    this.isWideScreen = this.breakpointObserver
        .observe(['(min-width: 821px)'])
        .pipe(map(({matches}) => matches));
    this.isSmallScreen = this.breakpointObserver
        .observe(['(max-width: 820px)'])
        .pipe(map(({matches}) => matches));
  }

  async ngOnInit(): Promise<void> {
    const candidatId = localStorage.getItem('candidatId');
    this.candidatId = candidatId as unknown as number;

    if (!this.candidatConnectedService.isLoggedIn()) {
      this.authentifierIndividu();
    }
    this.isFullyBooked = false;
    this.previousUrl.subscribe(url => {
      this.originUrl = url;
    });
    this.sub = this.route.params.subscribe(params => {
      this.eventId = +params.eventId;
    });
    if (this.eventId) {
      localStorage.setItem('eventId', this.eventId as unknown as string);
    } else {
      this.eventId = Number(localStorage.getItem('eventId'));
    }
    this.eventService.getEventById(this.eventId).subscribe(datas => {
      this.currentEvent = datas;
      if (this.currentEvent !== undefined) {
        this.event = this.currentEvent;

        if (this.event) {
          this.getEventOffers();
          this.event.modeAcceesList.forEach((modaliteAcceesList: any) => {
            // tslint:disable-next-line:forin
            if (modaliteAcceesList.modaliteAcces.id === 1) {
              this.nbPlaceEnPhysique = modaliteAcceesList.nombrePlace - modaliteAcceesList.nombreInscrit;
            }
            if (modaliteAcceesList.modaliteAcces.id === 0) {
              this.nbPlaceEnLigne = modaliteAcceesList.nombrePlace - modaliteAcceesList.nombreInscrit;
            }
            if (this.nbPlaceEnPhysique === 0 && this.nbPlaceEnLigne === 0) {
              this.isFullyBooked = true;
            }
          });
          this.getBenefices();
          this.getPublicTarget();
        }
      }
      this.getEventObjectivesAndType();
      this.getPicture();
      this.getEventLocation();
      this.shareUrl = this.urlService.eventUrlToShare(this.currentEvent?.id);
      return;
    });
    this.isCandidatIscrit(this.eventId, this.candidatConnectedService?.getCandidatConnected()?.id);

  }

  public getEventLocation(): string | undefined {
    if (this.currentEvent?.codePostal && this.currentEvent?.ville) {
      this.eventLocation = this.currentEvent.codePostal + ' ' + this.currentEvent?.ville;
    } else if (
        (!this.currentEvent?.codePostal && !this.currentEvent?.ville) &&
        (this.nbPlaceEnPhysique !== undefined && this.nbPlaceEnPhysique > 0)
    ) {
      this.eventLocation = undefined;
    } else {
      this.eventLocation = 'A distance';
    }
    return this.eventLocation;
  }

  public isEventFull(event: IEvent): boolean {
    return this.getOnSitePlaces(event) === 0 && this.getRemotePlaces(event) === 0;
  }

  public getOnSitePlaces(event: IEvent): number {
    this.onSitePlace = event.modeAcceesList.filter(modality => {
      return modality.modaliteAcces.id === 1;
    });
    return this.onSitePlace[0] ? this.onSitePlace[0].nombrePlace - this.onSitePlace[0].nombreInscrit : 0;
  }

  public getRemotePlaces(event: IEvent): number {
    this.remotePlace = event.modeAcceesList.filter(modality => {
      return modality.modaliteAcces.id === 0;
    });
    return this.remotePlace[0] ? this.remotePlace[0].nombrePlace - this.remotePlace[0].nombreInscrit : 0;
  }

  public async getEventOffers(): Promise<void> {
    this.event?.offres.forEach((offre: IOffer) => {
      this.offerService.getOfferById(offre.id).subscribe(offreEsd => {
        if (offreEsd) {
          this.offerTab.push(offreEsd);
        }
      }, err => {
      });
    });
  }

  public connexionConfirmation(): void {
    const confirmDialog = this.dialog.open(DialogConfirmationComponent, {
      data: {
        message: 'Gagnez du temps pour vous inscrire en vous connectant avec vos identifiants Pôle Emploi',
        buttonText: {
          ok: 'Se connecter avec pole emploi',
          cancel: ''
        }
      }
    });
    confirmDialog.afterClosed().subscribe(result => {
      if (result) {
        // console.log('result : ', result);
      }
    }, err => {
      // console.log('HTTP Error', err);
    });
  }

  public goToLinkUrl(url: string): any {
    window.open(url, '_blank');
  }

  private getBenefices(): void {
    this.beneficesList = this.event?.tags.filter(tag => {
      return tag.typeTagId === this.benefices;
    });
  }

  private getPicture(): void {
    const tagRecrutement = this.currentEvent?.tags.find(item => item.id === 19);
    const tagDecouverteMetier = this.currentEvent?.tags.find(item => item.id === 20);
    if (tagRecrutement !== undefined && tagRecrutement !== null) {
      this.afficherImageRecrutement = true;
      this.afficherImageFormation = false;
      this.afficherImageDecouverteMetier = false;
    } else if (tagDecouverteMetier !== undefined && tagDecouverteMetier !== null) {
      this.afficherImageRecrutement = false;
      this.afficherImageFormation = false;
      this.afficherImageDecouverteMetier = true;
    } else {
      this.afficherImageRecrutement = false;
      this.afficherImageFormation = true;
      this.afficherImageDecouverteMetier = false;
    }
    const tagTousMobilises = this.currentEvent?.tags.find(item => item.id === 26);
    if (tagTousMobilises !== undefined && tagTousMobilises !== null) {
      this.afficherImageTousMobilises = true;
    }
    else {
      this.afficherImageTousMobilises = false;
    }
  }

  private getPublicTarget(): void {
    this.publicCiblesList = this.event?.tags.filter(tag => {
      return tag.typeTagId === this.publicCibles;
    });
  }

  private getEventObjectivesAndType(): void {
    this.publicObjectivesList = this.event?.tags.filter(objective => {

      return objective.typeTagId === 2;
    });
    const objectivesList = this.event?.tags.filter(objective => {

      return objective.typeTagId === 3;
    });
    this.publicObjectivesList.push.apply(this.publicObjectivesList, objectivesList);
  }

  public shareWithTwitter(): void {
    this.router.navigate([]).then(result => {
      window.open(`https://twitter.com/share?url=https://${this.shareUrl}`, '_blank');
    });
  }

  public shareWithLinkedin(): void {
    this.router.navigate([]).then(result => {
      window.open(`https://www.linkedin.com/shareArticle?mini=true&amp;url=${this.shareUrl}`, '_blank');
    });
  }

  public shareWithFacebook(): void {
    this.router.navigate([]).then(result => {
      window.open(`https://www.facebook.com/sharer.php?u=${this.shareUrl}`, '_blank');
    });
  }

  ngOnDestroy(): void {
    this.sub.unsubscribe();
  }


  public annulerInscription(event: any): void {
    const confirmDialog = this.dialog.open(DialogAnnulationComponent, {
      data: {
        title: 'Annuler ma participation',
        message: 'Etes-vous certain·e de vouloir annuler votre particpation à cette rencontre professionnelle ?',
        buttonText: {
          ok: 'Confirmer mon annulation',
          cancel: 'ANNULER'
        }
      }
    });
    const snack = this.snackBar.open('Confirmation de la suppression');
    confirmDialog.afterClosed().subscribe(result => {
      snack.dismiss();
      this.snackBar.open('Vous avez annulé votre participation!', '', {
        duration: 2000,
      });
      if (result === true) {
        this.updateCandidatEvenement(this.eventId, 3);
        this.setSaving(event, 'Je m\'inscris');
        window.location.reload();
        window.setTimeout(() => {
          this.inscriptionAnnulee = true;
        }, 40);
      } else {
        this.snackBar.open('Vous n\'avez pas annulé votre participation!', '', {
          duration: 2000,
        });
      }
    });
  }

  private updateCandidatEvenement(eventId: number | undefined, statutInscription: number): void {
    this.candidatSerivce.updateCandidatEvenement(eventId, statutInscription).subscribe(datas => {
          if (this.candidatConnected) {
            this.candidatConnected.annulation = true;
            if (this.candidatConnected.statutInscription !== undefined) {
              this.candidatConnected.statutInscription = this.annulationID;
            }
          }
          return datas;
        }, err => {
          if (err?.status === 500) {
            this.router.navigate(['/ErrorInscription']);
          }
        }
    );
  }

  setSaving(element: any, text: string): void {
    element.textContent = text;
  }

  showButtonCancel(): boolean {
    const ret = this.candidatConnectedService.isLoggedIn()
        && this.dejaInscrit;
    return ret;
  }

  showButtonAnnulation(): boolean {
    console.log('this.statusInscrit', this.statusInscrit);
    const ret = this.candidatConnectedService.isLoggedIn()
        && this.statusInscrit && this.dejaInscrit === false;
    return ret;
  }

  showButtonSign(): boolean {
    console.log('this.dejaInscrit', this.dejaInscrit);
    const ret1 = this.candidatConnectedService.isLoggedIn()
        && !this.dejaInscrit && !this.statusInscrit;
    return ret1;
  }

  private authentifierIndividu(): any {
    const peConnectPayload = this.peConnectS.getPayloadPeConnect();
    if (peConnectPayload != null) {
      peConnectPayload.code = this.activatedRoute.snapshot.queryParams.code;
      this.peConnectS.storePeConnectPayload(peConnectPayload);
      if (this.activatedRoute.snapshot.queryParams.state === peConnectPayload.state) {
        this.candidatSerivce.authentifier(peConnectPayload).subscribe((candidat: ICandidat) => {
          if (candidat) {
            localStorage.setItem('candidatId', candidat?.id as unknown as string);
            this.candidatConnectedService.saveCandidatConnected(candidat);
            this.sessionPeConnectExpiredS.startCheckUserInactivity(candidat.peConnectAuthorization?.expireIn);
            // @ts-ignore
            this.candidatConnectedService?.candidatConnected?.id = candidat?.id;
            const http$ = this.candidatSerivce.isCandidatInscrit(this.eventId, this.candidatConnectedService?.candidatConnected?.id);
            http$.subscribe(datas => {
              this.statusInscrit = datas.inscrit;
            });
          }
        }, (error) => {
          this.peConnectS.logout();
          this.router.navigate([RoutesEnum.AUTHENTICATION_KO]);
        });
      }
    }
  }

  private isCandidatIscrit(eventId: number | undefined, candidatId: number | undefined): any {
    const http$ = this.candidatSerivce.isCandidatInscrit(eventId, candidatId);
    http$.subscribe(datas => {
      this.dejaInscrit = datas.inscrit;
    });
  }

  showButtonInscription(): boolean {
    const ret1 = this.candidatConnectedService.isLoggedIn()
        && this.dejaInscrit;
    return ret1;
  }

}
