import {Component, Input, OnInit} from '@angular/core';
import {IEvent} from '../../../model/IEvent';
import {ActivatedRoute, Router} from '@angular/router';
import {EventApiService} from '../../../shared/services/event-api.service';
import {Location} from '@angular/common';
import {IModeAcees} from '../../../model/IModeAccess';
import {ModaliteAccesService} from '../../../shared/services/modalite-acces.service';
import {CandidatService} from '../../../shared/services/candidat.service';
import {MatSnackBar} from '@angular/material/snack-bar';

@Component({
  selector: 'app-inscription',
  templateUrl: './inscription.component.html',
  styleUrls: ['./inscription.component.scss']
})
export class InscriptionComponent implements OnInit {
  @Input() event: IEvent | undefined;
  public eventId: number | undefined;
  public currentEvent: IEvent | undefined;
  private sub: any;
  public nbEplaceEnLigne: number | undefined;
  public nbEplaceEnPhisique: number | undefined;
  public IdModaliteSurPlace = 1;
  public IdMOdaliteEnLigne = 0;
  inscrit = true;

  constructor(
      private route: ActivatedRoute,
      private eventService: EventApiService,
      private snackBar: MatSnackBar,
      private location: Location,
      private modaliteAccesService: ModaliteAccesService,
      private router: Router,
      private activatedRoute: ActivatedRoute,
      private candidatSerivce: CandidatService,
      ) { }

  ngOnInit(): void {
    this.sub = this.route.params.subscribe(params => {
      this.eventId = +params.eventId;
    });

    this.eventService.getEventById(this.eventId).subscribe(datas => {
      this.currentEvent = datas;
      if (this.currentEvent !== undefined) {
        this.event = this.currentEvent;
        if (this.event) {
          this.event.modeAcceesList.forEach(modeAcceesList => {
            if (modeAcceesList.modaliteAcces.id === 0) {
              this.nbEplaceEnLigne = modeAcceesList.nombrePlace - modeAcceesList.nombreInscrit;
            }
            if (modeAcceesList.modaliteAcces.id === 1) {
              this.nbEplaceEnPhisique = modeAcceesList.nombrePlace - modeAcceesList.nombreInscrit;
            }
          });
        }
        this.event = this.currentEvent;
      }
      return;
    });
  }

  public confirmerInscriptionSurPlace(): void {
    this.inscrireCandidat(this.eventId, 1, true);

    if (this.inscrit) {
      this.router.navigate(['/confirmationSurPlace']);
    }
  }

  public confirmerInscriptionEnLigne(): void {
    this.inscrireCandidat(this.eventId, 0, true);

    if (this.inscrit) {
      this.router.navigate(['/confirmationEnLigne']);
    }
  }

  private inscrireCandidat(eventId: number | undefined, modaliteId: number | undefined, prerequisValider: boolean): void {
    this.candidatSerivce.createCandidatEvenement(eventId, modaliteId, prerequisValider).subscribe(datas => {
          this.inscrit = true;

          return datas;
        }, err => {
          this.inscrit = false;
          if (err?.status === 403) {
            this.inscrit = false;
            this.router.navigate(['/placesNonDisponibles']);
          }
          if (err?.status === 409) {
            this.inscrit = false;
            this.router.navigate(['/DejaInscrit']);
          }
          if (err?.status === 500) {
            this.inscrit = false;
            this.router.navigate(['/ErrorInscription']);
          }
          if (err?.status === 400) {
            this.inscrit = false;
            this.router.navigate(['ErrorInscription']);
          }
        }
    );
  }
}
