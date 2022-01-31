import {Component, EventEmitter, Input, OnDestroy, OnInit, Output} from '@angular/core';
import {ICandidat} from '../../../../model/ICandidat';
import {CandidatService} from '../../../../shared/services/candidat.service';
import {EventApiService} from '../../../../shared/services/event-api.service';
import {ActivatedRoute} from '@angular/router';
import {DialogConfirmationComponent} from '../../../../shared/components/dialog-confirmation/dialog-confirmation.component';
import {MatSnackBar} from '@angular/material/snack-bar';
import {MatDialog} from '@angular/material/dialog';


@Component({
  // tslint:disable-next-line:component-selector
  selector: 'tr[app-details-candidat]',
  templateUrl: './details-candidat.component.html',
  styleUrls: ['./details-candidat.component.scss']
})
export class DetailsCandidatComponent implements OnInit, OnDestroy {
  @Input() candidat: ICandidat | undefined;
  @Output() delete = new EventEmitter<ICandidat>();
  eventId: number | undefined;
  private sub: any;
  public checked: boolean | undefined = false;
  @Output() newPresenceEvent = new EventEmitter<ICandidat>();

  constructor(private candidatService: CandidatService,
              private snackBar: MatSnackBar,
              private dialog: MatDialog,
              private eventService: EventApiService,
              private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.sub = this.route.params.subscribe(params => {
      this.eventId = +params.eventId;
    });

    this.checked = this.candidat?.present;
  }


  ngOnDestroy(): void {
    this.sub.unsubscribe();
  }

  public change(e: any): any {
    if (this.candidat !== undefined) {
      this.candidat.present = e.checked;
      this.presenceCandidatEvent(this.eventId, this.candidat);
      this.newPresenceEvent.emit(this.candidat);

    }
  }

  public presenceCandidatEvent(idEvent: number | undefined, candidat: ICandidat | undefined): void {
    this.candidatService.presenceCandidatEvent(idEvent, candidat).subscribe(datas => {
      this.candidat = datas;
    });
  }


  public deleteCandidat(): void {
    const confirmDialog = this.dialog.open(DialogConfirmationComponent, {
      data: {
        title: 'Supprimer un candidat',
        message: 'Souhaitez-vous bien supprimer ce candidat à cette rencontre ?',
        buttonText: {
          ok: 'SUPPRIMER',
          cancel: 'ANNULER'
        }
      }
    });
    const snack = this.snackBar.open('Confirmation de la suppression');
    confirmDialog.afterClosed().subscribe(result => {
      snack.dismiss();
      this.snackBar.open('Le candidat a bien été supprimé', '', {
        duration: 2000,
      });
      if (result === true) {
        this.candidatService.deleteCandidatById(this?.eventId, this.candidat?.id);
        this.delete.emit(this.candidat);
        window.location.reload();
      } else {
        this.snackBar.open('Le candidat n\'a pas été été supprimé', '', {
          duration: 2000,
        });
      }
    });
  }
}
