import {Component, OnInit} from '@angular/core';
import {FormArray, FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {Observable, BehaviorSubject} from "rxjs";

import {SessionPeConnectExpiredService} from '../../../shared/services/session-pe-connect-expired.service';
import {CandidatConnectedService} from '../../../shared/services/candidat-connected.service';
import {ModaliteAccesService} from '../../../shared/services/modalite-acces.service';
import {PeConnectService} from '../../../shared/services/pe-connect.service';
import {EventApiService} from '../../../shared/services/event-api.service';
import {CandidatService} from '../../../shared/services/candidat.service';
import {UrlService} from "../../../shared/services/url.service";

import {IModeAcees} from '../../../model/IModeAccess';
import {RoutesEnum} from '../../../model/routes.enum';
import {ICandidat} from '../../../model/ICandidat';
import {IEvent} from '../../../model/IEvent';
import {IsRegisteredService} from "../../../shared/services/is-registered.service";

@Component({
    selector: 'app-prerequisites',
    templateUrl: './prerequisites.component.html',
    styleUrls: ['./prerequisites.component.scss']
})
export class PrerequisitesComponent implements OnInit {

    private eventId: number | undefined;
    public event: IEvent | undefined;
    private remotePlaceNumber: number | undefined;
    private onSitePlaceNumber: number | undefined;
    private inscrit = true;
    private previousUrl: Observable<string> = this.urlService.previousUrl$;
    private originUrl: string = '';
    private typeEventId = 2;

    public form: any;
    public prerequisiteForm: FormGroup | undefined;
    public ID_REMOTE_MODALITY = 0;
    public ID_ONSITE_MODALITY = 1;

    isRegisteredStatus: boolean | undefined;

    constructor(
        private router: Router,
        private activatedRoute: ActivatedRoute,
        private fb: FormBuilder,
        private eventS: EventApiService,
        private candidatS: CandidatService,
        private modaliteAccesS: ModaliteAccesService,
        private peConnectS: PeConnectService,
        private candidatConnectedS: CandidatConnectedService,
        private sessionPeConnectExpiredS: SessionPeConnectExpiredService,
        private urlService: UrlService,
        private isRegisteredS: IsRegisteredService,
        ) {
        this.prerequisiteForm = this.fb.group({
            checkArray: this.fb.array([], [Validators.required])
        });
        // const subjectRegistration = new BehaviorSubject(this.inscrit);
        // this.isRegisteredS.checkIfRegistered(subjectRegistration);
    }

    ngOnInit(): void {
        this.previousUrl.subscribe(url => {
            this.originUrl = url;
        });
        if (!this.candidatConnectedS.isLoggedIn()) {
            this.authentifierIndividu();
        }
        this.eventId = localStorage.getItem('eventId') as unknown as number;
        this.eventS.getEventById(this.eventId).subscribe(event => {
            this.event = event;
            this.form = new FormGroup({
                libelle: new FormControl(false, [Validators.requiredTrue])
            });
        });
    }

    public getTypeEvent(event: IEvent) {
        return event.tags.filter(tag => {
            return tag.typeTagId === this.typeEventId;
        });
    }

    public onCheckboxChange(e: any): any {
    if (this.prerequisiteForm !== undefined) {
      const checkArray: FormArray = this.prerequisiteForm.get('checkArray') as FormArray;
      if (e.target.checked) {
        checkArray.push(new FormControl(e.target.value));
      } else {
        let i = 0;
        // @ts-ignore
        checkArray.controls.forEach((item: FormControl) => {
            if (item.value === e.target.value) {
                checkArray.removeAt(i);
                return;
            }
            i++;
        });
      }
    }
    }

    public submitForm(): any {
        const prerequisValider = this.prerequisiteForm?.value.checkArray.length === this.event?.prerequis.length;
        if (!prerequisValider) {
            this.inscrireCandidat(this.eventId, undefined, false);
            if (this.inscrit) {
                this.isRegisteredS.changeRegistrationStatus(this.inscrit);
                this.router.navigate(['/inscriptionKo']);
            }
        }
        let modalitePhysique: IModeAcees;
        let modaliteEnLigne: IModeAcees;
        this.modaliteAccesS.getAllModaliteAccessByEvent(this.eventId).subscribe(datas => {
            datas.forEach((modaliteAccees: any) => {
                if (modaliteAccees?.modaliteAcces?.id === this.ID_REMOTE_MODALITY) {
                    modaliteEnLigne = modaliteAccees;
                }
                if (modaliteAccees?.modaliteAcces?.id === this.ID_ONSITE_MODALITY) {
                    modalitePhysique = modaliteAccees;
                }
            });
            this.remotePlaceNumber = modaliteEnLigne?.nombrePlace;
            this.onSitePlaceNumber = modalitePhysique?.nombrePlace;
            if (modaliteEnLigne && !modalitePhysique && prerequisValider) {
                if (this.remotePlaceNumber !== undefined && this.remotePlaceNumber > 0) {
                    this.inscrireCandidat(this.eventId, modaliteEnLigne?.modaliteAcces?.id, true);
                    if (this.inscrit) {
                        this.isRegisteredS.changeRegistrationStatus(this.inscrit);
                        this.router.navigate(['/confirmationEnLigne']);
                    }
                }
            } else if (modalitePhysique && !modaliteEnLigne && prerequisValider) {
                if (this.onSitePlaceNumber !== undefined && this.onSitePlaceNumber > 0) {
                    this.inscrireCandidat(this.eventId, modalitePhysique?.modaliteAcces?.id, true);
                    if (this.inscrit) {
                        this.isRegisteredS.changeRegistrationStatus(this.inscrit);
                        this.router.navigate(['/confirmationSurPlace']);
                    }
                }
            } else if (prerequisValider) {
                if (this.onSitePlaceNumber !== undefined && this.onSitePlaceNumber > 0
                    && this.remotePlaceNumber !== undefined && this.remotePlaceNumber > 0) {
                    if (this.inscrit) {
                        this.isRegisteredS.changeRegistrationStatus(this.inscrit);
                        this.router.navigate(['/inscription/', this.eventId]);
                    }
                } else if (this.onSitePlaceNumber !== undefined && this.onSitePlaceNumber === 0
                    && this.remotePlaceNumber !== undefined && this.remotePlaceNumber === 0) {
                    this.router.navigate(['/placesNonDisponibles']);
                } else if (this.inscrit) {
                    this.isRegisteredS.changeRegistrationStatus(this.inscrit);
                    this.router.navigate(['/inscription/', this.eventId]);
                }
            }
        });
    }

    private inscrireCandidat(
        eventId: number | undefined, modaliteId: number | undefined, prerequisValider: boolean): void {
        this.candidatS.createCandidatEvenement(eventId, modaliteId, prerequisValider).subscribe(datas => {
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

    private authentifierIndividu(): any {
        const peConnectPayload = this.peConnectS.getPayloadPeConnect();
        if (peConnectPayload != null) {
            peConnectPayload.code = this.activatedRoute.snapshot.queryParams.code;
            this.peConnectS.storePeConnectPayload(peConnectPayload);
        }
        if (this.activatedRoute.snapshot.queryParams.state === peConnectPayload.state) {
            this.candidatS.authentifier(peConnectPayload).subscribe((candidat: ICandidat) => {
                if (candidat) {
                    this.candidatConnectedS.saveCandidatConnected(candidat);
                    this.sessionPeConnectExpiredS.startCheckUserInactivity(candidat.peConnectAuthorization?.expireIn);
                    this.router.navigate([RoutesEnum.PRE_REQUIS]);
                }
            }, (error) => {
                this.peConnectS.logout();
                this.router.navigate([RoutesEnum.AUTHENTICATION_KO]);
            });
        } else {
            this.router.navigate([RoutesEnum.EVENTS]);
        }
    }

}
