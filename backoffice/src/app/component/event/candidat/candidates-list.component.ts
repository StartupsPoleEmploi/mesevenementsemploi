import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {ICandidat} from '../../../model/ICandidat';
import {CandidatService} from '../../../shared/services/candidat.service';
import {ExportExcelService} from '../../../shared/services/export-excel.service';
import {ActivatedRoute, Router} from '@angular/router';
import {IEvent} from '../../../model/IEvent';
import {EventApiService} from '../../../shared/services/event-api.service';
import {Location} from '@angular/common';
import {NavbarService} from '../../../shared/services/navbar.service';
import {IModeAcees} from '../../../model/IModeAccess';
import {NavigationService} from '../../../shared/services/navigation.service';

@Component({
  selector: 'app-candidates-list',
  templateUrl: './candidates-list.component.html',
  styleUrls: ['./candidates-list.component.scss']
})
export class CandidatesListComponent implements OnInit, OnDestroy {
  eventId: number | undefined;
  prerequisValider = true;
  @Input() event: IEvent | undefined;

  public headers = ['ID DE', 'Nom', 'Prenom', 'Email', 'Modalité', 'Présence'];
  private sub: any;
  public candidats: ICandidat[] = [];
  public nbParticipant = 0;
  public remotePlace: IModeAcees[] | undefined;
  public onSitePlace: IModeAcees[] | undefined;

  constructor(private candidatService: CandidatService,
              private eventService: EventApiService,
              private exportExcelService: ExportExcelService,
              private route: ActivatedRoute,
              private router: Router,
              private navbarService: NavbarService,
              private location: Location,
              public navigation: NavigationService) {
    this.navbarService.show();
    this.navbarService.handleTabs(
      true,
      '',
      false,
      false,
      false,
      false,
      false,
      false,
      true,
      true,
    );
  }

  ngOnInit(): void {
    this.sub = this.route.params.subscribe(params => {
      this.eventId = +params.eventId;
    });
    this.getAllCandidatByEvent(this.eventId, this.prerequisValider);
    this.recupererEvent();
  }

  public recupererEvent(): void {
    this.eventService.getEventById(this.eventId).subscribe(datas => {
      this.event = datas;
      if(this.event) {
        if(!this.isEventFull(this.event)) {
          this.navbarService.handleTabs(
            true,
            this.event.titre,
            false,
            false,
            false,
            false,
            false,
            false,
            true,
            true,
          );
        } else {
          this.navbarService.handleTabs(
            true,
            this.event.titre,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            true,
          );
        }
      }
    });
  }

  public getAllCandidatByEvent(id: number | undefined, avecPrerequisValider: boolean): void {
    this.candidatService.getAllCandidatByEvent(id, avecPrerequisValider).subscribe(datas => {
      this.candidats = datas.sort((a: { nom: string; }, b: { nom: any; }) => a.nom.localeCompare(b.nom));
    });
  }

  public exportCandidats(): void {
    this.exportExcelService.generateExcelCandidatsInscrits(this.candidats, this.event);
  }

  public navigateToAddNewRegistered(): void {
    this.router.navigate(['addInscrit/' + this.eventId]);
  }

  public updatePresent(candidat: ICandidat): void {
    if (candidat?.present) {
      this.nbParticipant = this.nbParticipant + 1;
    } else {
      this.nbParticipant = this.nbParticipant - 1;
    }
    const index = this.candidats.findIndex((candidatItem: ICandidat) => {
      return candidatItem.id === candidat.id;
    });
    this.candidats.splice(index, 1, candidat);
  }

  public getNbInscritEnPhysique(): number {
    return this.getNombreInscrits(1);
  }

  public getNbInscritEnLigne(): number {
    return this.getNombreInscrits(0);
  }

  public getNombreInscrits(idModalite: number): number {
    const list = this.candidats.filter((candidat: ICandidat) => candidat.modaliteAccesId === idModalite);
    return list.length;
  }

  public getNbParticipant(): number {
    const list = this.candidats.filter((candidat: ICandidat) => candidat.present === true);
    return list.length;
  }

  public deleteCandidat(candidat: ICandidat): void {
    const index = this.candidats.indexOf(candidat);
    if (index > -1) {
      this.candidats.splice(index, 1);
    }
  }

  ngOnDestroy(): void {
    this.sub.unsubscribe();
  }

  public backClicked(): void {
    this.location.back();
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
}
