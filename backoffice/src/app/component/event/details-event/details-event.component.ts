import {Component, EventEmitter, Input, OnInit, Output, ViewChild} from '@angular/core';
import {IEvent} from '../../../model/IEvent';
import {IModeAcees} from '../../../model/IModeAccess';
import IModalityAccess from '../../../model/IModalityAccess';
import {EventApiService} from '../../../shared/services/event-api.service';
import IState from '../../../model/IState';
import {MatPaginator} from '@angular/material/paginator';
import {StateService} from '../../../shared/services/state.service';
import {MatDialog} from '@angular/material/dialog';
import {MatSnackBar} from '@angular/material/snack-bar';
import {Router} from '@angular/router';
import {DialogConfirmationComponent} from '../../../shared/components/dialog-confirmation/dialog-confirmation.component';
import {TagService} from '../../../shared/services/tag.service';
import ITag from '../../../model/ITag';
import {DatePipe} from '@angular/common';
import {ExportExcelService} from '../../../shared/services/export-excel.service';
import {ICandidat} from '../../../model/ICandidat';
import {CandidatService} from '../../../shared/services/candidat.service';


@Component({
  // tslint:disable-next-line:component-selector
  selector: 'tr[app-details-event]',
  templateUrl: './details-event.component.html',
  styleUrls: ['./details-event.component.scss'],
  preserveWhitespaces: false
})

export class DetailsEventComponent implements OnInit {

  public headers = ['Nom événement', 'Objectif', 'Date', 'Heure', 'Inscrits/Places totales', 'Statut', 'Action'];

  public modeAcees: IModeAcees[] = [];
  public modeAceesList: IModalityAccess[] = [];
  public state: IState | undefined;
  public remotePlace: IModeAcees[] | undefined;
  public onSitePlace: IModeAcees[] | undefined;
  @Input() event: IEvent | undefined;
  @Input() columns: string[] | undefined;
  @Output() delete = new EventEmitter<IEvent>();
  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator | undefined;
  public idTypeTagTypologie = 2;
  @Input() candidats: ICandidat[] = [];
  private eventApiService: EventApiService;
  @Output() exportExcel = new EventEmitter<ICandidat[]>();

  constructor(private eventService: EventApiService,
              private etatService: StateService,
              private tagService: TagService,
              private dialog: MatDialog,
              private snackBar: MatSnackBar,
              private router: Router,
              private exportExcelService: ExportExcelService,
              private candidatService: CandidatService,
              private datePipe: DatePipe) {
    this.eventApiService = eventService;


    this.modeAcees = [];
    this.modeAceesList = [];
  }

  ngOnInit(): void {
    this.getAllCandidatByEvent(this.event?.id, true);
  }

  public getTagsTypologie(): Array<ITag> {
    return this.getTagByTypeTags(this.idTypeTagTypologie);
  }

  public getTagByTypeTags(typeTag: number): Array<ITag> {
    if (this.event?.tags !== undefined) {
      return this.event?.tags
        .filter((tag: ITag) => tag.typeTagId === typeTag);
    }
    return new Array<ITag>();
  }

  public onDeleteEvent(): void {
    const confirmDialog = this.dialog.open(DialogConfirmationComponent, {
      data: {
        title: 'Confirmation de la suppression',
        message: 'Attention: Si vous confirmer la suppression, vous allez perdre toute vos donner,\nConfirmez-vous la suppression de cette événement ? '
      }
    });

    const snack = this.snackBar.open('Confirmation de la suppression');
    confirmDialog.afterClosed().subscribe(result => {
      snack.dismiss();
      this.snackBar.open('L\'événement a bien été supprimé', '', {
        duration: 2000,
      });
      if (result === true) {
        this.eventService.deleteEventById(this.event?.id);
        this.delete.emit(this.event);
      } else {
        this.snackBar.open('L\'événement n\'a pas été été supprimé', '', {
          duration: 2000,
        });
      }
    });
  }

  public async updateEvent(event: IEvent | undefined): Promise<any> {
    this.router.navigate(['editer/evenement/' + event?.id]);
  }

  public async getAllCandidatByEvent(id: number | undefined, avecPrerequisValider: boolean): Promise<any> {
    this.candidatService.getAllCandidatByEvent(id, avecPrerequisValider).subscribe(datas => {
      this.candidats = datas;
    });
  }

  public exportCandidats(): void {
    this.exportExcelService.generateExcelCandidatsInscrits(this.candidats, this.event);
  }

  public getNombreInscrits(modAcces: IModeAcees): number {
    const list = this.candidats.filter((candidat: ICandidat) => candidat.modaliteAccesId === modAcces.modaliteAcces.id);
    return list.length;
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
