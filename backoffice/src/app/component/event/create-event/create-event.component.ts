import {Component, Input, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {BreakpointObserver, Breakpoints} from '@angular/cdk/layout';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {MatDialog} from '@angular/material/dialog';
import {DatePipe} from '@angular/common';
import {ActivatedRoute, Router} from '@angular/router';
import {Subject} from 'rxjs';

import {ModalityAccessService} from '../../../shared/services/modality-access.service';
import {PrerequisitesService} from '../../../shared/services/prerequisites.service';
import {EventApiService} from '../../../shared/services/event-api.service';
import {NavbarService} from '../../../shared/services/navbar.service';
import {StateService} from '../../../shared/services/state.service';
import {TagService} from '../../../shared/services/tag.service';
import {RomeComponent} from './rome/rome.component';

import IModalityAccess from '../../../model/IModalityAccess';
import {IPrerequisites} from '../../../model/IPrerequisites';
import {IOffer} from '../../../model/IOffer';
import {IRome} from '../../../model/IRome';
import IState from '../../../model/IState';
import {INaf} from '../../../model/INaf';
import ITag from '../../../model/ITag';
import {NafComponent} from './naf/naf.component';
import {TagsComponent} from './tags/tags.component';
import {PrerequisitesComponent} from './prerequisites/prerequisites.component';
import {RecruitersComponent} from './recruiters/recruiters.component';
import {OfferComponent} from './offer/offer.component';
import {DialogConfirmationComponent} from '../../../shared/components/dialog-confirmation/dialog-confirmation.component';
import {IEvent} from '../../../model/IEvent';
import {ToolbarComponent} from '../../../shared/components/toolbar/toolbar.component';
import {ApiService} from 'src/app/shared/services/api.service';

@Component({
  selector: 'app-create-event',
  templateUrl: './create-event.component.html',
  styleUrls: ['./create-event.component.scss']
})

export class CreateEventComponent implements OnInit, OnDestroy {
  @Input() checked: boolean;

  public romeComponent: RomeComponent | undefined;
  public nafComponent: NafComponent | undefined;
  public tagsComponent: TagsComponent | undefined;
  public prerequisComponent: PrerequisitesComponent | undefined;
  public recruitersComponent: RecruitersComponent | undefined;
  public offerComponent: OfferComponent | undefined;
  public tagsList: ITag[];
  public stateList: IState[];
  public modalityAccessList: IModalityAccess[];
  public prerequisitesList: IPrerequisites[];
  public offersList: IOffer[] = [];
  public eventToUpdate: IEvent | undefined;

  private unsubscribe = new Subject<void>();
  public minDate: Date = new Date();
  private modalityAccess = new Array();

  public settingsTitle = 'Paramètres généraux';
  public featuresTitle = 'Caractéristiques';
  public requirementsTitle = 'Pré-requis candidats';
  public detailsTitle = 'Détails de l\'évènement';
  public identifier: string | null = '';
  public label = '';

  public createEventForm: any;
  private event: any;

  public stateId!: number;
  private eventId: number | undefined;
  public DRAFT_ID = 0;
  public PUBLISHED_ID = 1;
  public REMOTE_MODALITY_ID = 0;
  public ONSITE_MODALITY_ID = 1;

  public creationMode = false;
  public onSiteChecked = false;
  public remoteChecked = false;
  public isPrivate = true;
  public isSettings = true;
  public isFeatures = false;
  public isRequirements = false;
  public isDetails = false;
  public isMobile = true;
  public isPublished = false;
  public validationDraftState = false;
  public validationState = false;
  public tagEventControl = false;
  public prerequisiteEventControl = false;
  public settingsControl = false;
  public featuresControl = false;
  public descriptionControl = false;
  public modalityControl = false;
  public editionMode = false;
  public isLoading: boolean | undefined;

  constructor(
    private eventApiS: EventApiService,
    private tagS: TagService,
    private statesS: StateService,
    private modalityAccessS: ModalityAccessService,
    private prerequisiteS: PrerequisitesService,
    public datepipe: DatePipe,
    private dialog: MatDialog,
    private navbarService: NavbarService,
    private breakpointObserver: BreakpointObserver,
    private router: Router,
    private route: ActivatedRoute,
    private apiService: ApiService,
  ) {
    this.navbarService.show();
    this.navbarService.handleTabs(
      true,
      'Créer un évènement',
      false,
      false,
      true,
      false,
      false,
      false,
      false,
      false,
    );
    this.breakpointObserver.observe([Breakpoints.Handset]).subscribe((result) => {
      this.isMobile = result.matches;
    });
    this.checked = false;
    this.minDate = new Date();
    this.stateList = [];
    this.modalityAccessList = [];
    this.prerequisitesList = [];
    this.tagsList = [];
  }

  @ViewChild('romeComponent') set romeContent(content: RomeComponent) {
    if (content) { // initially setter gets called with undefined
      this.romeComponent = content;
      const code = this.createEventForm?.get('romeId').value;
      if (code) {
        this.romeComponent.setValue(code);
      }
    }
  }

  @ViewChild('nafComponent') set nafContent(content: NafComponent) {
    if (content) { // initially setter gets called with undefined
      this.nafComponent = content;
      const code = this.createEventForm?.get('nafCode').value;
      if (code) {
        this.nafComponent.setValue(code);
      }
    }
  }

  @ViewChild('tagsComponent') set tagsContent(content: TagsComponent) {
    if (content) { // initially setter gets called with undefined
      this.tagsComponent = content;
      const code = this.createEventForm?.get('tags').value;
      if (code) {
        this.tagsComponent.setValue(code);
      }
    }
  }

  @ViewChild('prerequisComponent') set prerequisContent(content: PrerequisitesComponent) {
    if (content) { // initially setter gets called with undefined
      this.prerequisComponent = content;
      const code = this.createEventForm?.get('prerequis').value;
      if (code) {
        this.prerequisComponent.setValue(code);
      }
    }
  }

  @ViewChild('recruitersComponent') set recruitersContent(content: RecruitersComponent) {
    if (content) {
      this.recruitersComponent = content;
      const recruiter = this.createEventForm?.get('intervenants').value;
      if (recruiter) {
        this.recruitersComponent.setValue(recruiter);
      }
    }
  }

  @ViewChild('offerComponent') set offerContent(content: OfferComponent) {
    if (content) {
      this.offerComponent = content;
      const offer = this.createEventForm?.get('offres').value;
      if (offer) {
        this.offerComponent.setValue(offer);
      }
    }
  }

  @ViewChild('toolbarComponent') toolbarComponent: ToolbarComponent | undefined;

  get eventTitle(): FormControl {
    return this.createEventForm.get('titre');
  }
  get eventDate(): FormControl {
    return this.createEventForm.get('dateEvenement');
  }
  get eventStartHour(): FormControl {
    return this.createEventForm.get('heureDebut');
  }
  get eventEndHour(): FormControl {
    return this.createEventForm.get('heureFin');
  }
  get eventAddress(): FormControl {
    return this.createEventForm.get('adresse');
  }
  get eventPostalCode(): FormControl {
    return this.createEventForm.get('codePostal');
  }
  get eventCity(): FormControl {
    return this.createEventForm.get('ville');
  }
  get eventRomeId(): FormControl {
    return this.createEventForm.get('romeId');
  }
  get eventNafCode(): FormControl {
    return this.createEventForm.get('nafCode');
  }
  get eventRemoteModality(): FormControl {
    return this.createEventForm.get('remoteModality');
  }
  get eventAvailableRemotePlaces(): FormControl {
    return this.createEventForm.get('availableRemotePlaces');
  }
  get eventOnSiteModality(): FormControl {
    return this.createEventForm.get('onSiteModality');
  }
  get eventAvailableOnSitePlaces(): FormControl {
    return this.createEventForm.get('availableOnSitePlaces');
  }
  get eventUrl(): FormControl {
    return this.createEventForm.get('urlParticipation');
  }
  get eventDescription(): FormControl {
    return this.createEventForm.get('description');
  }
  get eventProgress(): FormControl {
    return this.createEventForm.get('deroulement');
  }
  get eventInformationLink(): FormControl {
    return this.createEventForm.get('lienPlusInformation');
  }
  get eventInformation(): FormControl {
    return this.createEventForm.get('libelleLienPlusInformation');
  }
  get eventOffers(): FormControl {
    return this.createEventForm.get('offres');
  }
  get eventRecruiters(): FormControl {
    return this.createEventForm.get('intervenants');
  }
  get eventPrerequisites(): FormControl {
    return this.createEventForm.get('prerequis');
  }
  get eventTags(): FormControl {
    return this.createEventForm.get('tags');
  }
  get eventPublished(): FormControl {
    return this.createEventForm.get('estApublier');
  }

  ngOnInit(): void {
    this.statesS.getAllStates().subscribe(datas => {
      this.stateList = datas;
    });
    this.tagS.getAllTypesTags().subscribe(datas => {
      this.tagsList = datas;
    });
    this.modalityAccessS.getAllModalityAccess().subscribe(datas => {
      this.modalityAccessList = datas;
    });
    this.prerequisiteS.getAllPrerequisites().subscribe(datas => {
      this.prerequisitesList = datas;
    });
    this.route.params.subscribe(params => {
      this.eventId = +params.eventId;
      if (this.eventId && !this.eventToUpdate) {
        this.creationMode = false;
        this.editionMode = true;
        this.eventApiS.getEventById(this.eventId).subscribe(datas => {
          this.eventToUpdate = datas;
          this.createForm();
          this.setValueToUpdate();
          if (this.eventToUpdate?.etatId === 0) {
            this.navbarService.handleTabs(
              true,
              '',
              false,
              true,
              true,
              false,
              false,
              false,
              false,
              false,
            );
          } else {
            this.navbarService.handleTabs(
              true,
              this.eventToUpdate?.titre,
              false,
              true,
              true,
              false,
              false,
              false,
              false,
              false,
            );
          }
        });
      } else if (!this.eventToUpdate) {
        this.creationMode = true;
        this.editionMode = false;
        this.createForm();
        //this.navbarService.handleTabsCreation();
        this.navbarService.handleTabs(
          true,
          'Créer un évènement',
          false,
          true,
          true,
          false,
          false,
          false,
          false,
          false,
        );
      }
    });
  }

  private createForm(): void {
    this.createEventForm = new FormGroup({
      dateEvenement: new FormControl(null, [Validators.required]),
      description: new FormControl(null, [Validators.required, Validators.maxLength(1000)]),
      deroulement: new FormControl(null, [Validators.maxLength(1000)]),
      heureDebut: new FormControl(null, [Validators.required]),
      heureFin: new FormControl(null, [Validators.required]),
      estApublier: new FormControl(this.isPrivate, [Validators.required]),
      etatId: new FormControl(0),
      adresse: new FormControl(null),
      codePostal: new FormControl(null),
      ville: new FormControl(null),
      nafCode: new FormControl(null, [Validators.required]),
      timeZone: new FormControl('Europe/Paris'),
      titre: new FormControl(null, [Validators.required, Validators.maxLength(300)]),
      romeId: new FormControl(
        null,
        [Validators.required]
      ),
      offres: new FormControl(null),
      prerequis: new FormControl(null, [Validators.required]),
      tags: new FormControl(null, [Validators.required]),
      intervenants: new FormControl(null, [Validators.required]),
      urlParticipation: new FormControl({
        value: null,
        disabled: this.remoteChecked
      }, [Validators.required, Validators.maxLength(1500)]),
      libelleLienPlusInformation: new FormControl(null),
      lienPlusInformation: new FormControl(null),
      remoteModality: new FormControl(false),
      availableRemotePlaces: new FormControl({value: null, disabled: !this.remoteChecked}, [Validators.required]),
      onSiteModality: new FormControl(false),
      availableOnSitePlaces: new FormControl({value: null, disabled: !this.onSiteChecked}, [Validators.required]),
    });
  }

  public getTagsByCategories(category: string | any[], tags: any[]): number[] {
    if (category && category.length > 0) {
      if (typeof category !== 'string') {
        category.forEach(item => {
          tags.push({id: item});
        });
      }
    }
    return tags;
  }

  public addPrerequisites(newPrerequisites: any): void {
    this.eventPrerequisites.setValue(newPrerequisites);
  }

  public addRecruiters(newRecruiters: any): void {
    this.eventRecruiters.setValue(newRecruiters);
  }

  public addOffers(newOffers: any): void {
    const offer = {id: newOffers.id};
    this.offersList.push(offer);
    this.eventOffers.setValue(this.offersList);
  }

  public removeOffers(offerToRemove: any): void {
    this.offersList = this.eventOffers.value;
    const index = this.offersList.findIndex(offer => offer.id === offerToRemove.id);
    this.offersList.splice(index, 1);
    this.eventOffers.setValue(this.offersList);
  }

  public addRome(newRome: IRome): void {
    if (newRome) {
      this.eventRomeId.setValue(newRome);
    }
  }

  public addNaf(newNaf: INaf): void {
    this.eventNafCode.setValue(newNaf);
  }

  public addTags(newTags: any): void {
    this.eventTags.setValue(newTags);
  }

  public fillTag(tagControl: any): void {
    this.tagEventControl = tagControl;
  }

  public validateTagControl(tagControl: any): void {
    this.featuresControl = tagControl;
  }

  public fillPrerequisites(prerequisiteControl: any): void {
    this.prerequisiteEventControl = prerequisiteControl;
  }

  public checkboxOnSiteVisibility(checked: any): void {
    this.onSiteChecked = checked;
    this.modalityControl = false;
    if (!this.onSiteChecked && !this.remoteChecked) {
      this.eventAvailableRemotePlaces.setValue(null);
      this.eventAvailableOnSitePlaces.setValue(null);
      this.modalityControl = true;
    }
    this.handleIsOnSiteFields(this.onSiteChecked);
  }

  public checkboxRemoteVisibility(checked: any): void {
    this.remoteChecked = checked;
    this.modalityControl = false;
    if (!this.remoteChecked && !this.onSiteChecked) {
      this.eventAvailableRemotePlaces.setValue(null);
      this.eventAvailableOnSitePlaces.setValue(null);
      this.modalityControl = true;
    }
    this.handleIsRemoteFields(this.remoteChecked);
  }

  public checkboxEventVisibility(): boolean {
    this.isPrivate = !this.isPrivate;
    return this.isPrivate;
  }

  public displaySettings(): void {
    this.isSettings = true;
    this.isFeatures = false;
    this.isRequirements = false;
    this.isDetails = false;
  }

  public displayFeatures(): void {
    this.isSettings = false;
    this.isFeatures = true;
    this.isRequirements = false;
    this.isDetails = false;
  }

  public displayRequirements(): void {
    this.isSettings = false;
    this.isFeatures = false;
    this.isRequirements = true;
    this.isDetails = false;
  }

  public displayDetails(): void {
    this.isSettings = false;
    this.isFeatures = false;
    this.isRequirements = false;
    this.isDetails = true;
  }

  public saveTo(newStateId: number): void {
    if (newStateId !== null) {
      this.stateId = newStateId;
      if (this.stateId === this.DRAFT_ID) {
        this.isPublished = false;
        this.onSubmit(this.stateId, this.isPublished);
      } else {
        this.isPublished = true;
        this.onSubmit(this.stateId, this.isPublished);
      }
    }
  }

  public pushModality(modalite: number, nbPlaces: number, urlParticipation: string) {
    this.modalityAccess.push({
      modaliteAcces: {
        id: modalite
      },
      nombreInscrit: 0,
      nombrePlace: nbPlaces,
      nombrePresent: 0,
      urlParticipation
    });
  }

  public onSubmit(stateId: number, isPublished: boolean): void {

    if (
      (stateId === this.DRAFT_ID && !this.validatePartialFields(this.createEventForm)) ||
      (stateId === this.PUBLISHED_ID && !this.validateAllFormFields(this.createEventForm))) {
      let tags2 = new Array();
      tags2.push({id: this.eventTags.value?.eventType});
      tags2 = this.getTagsByCategories(this.eventTags.value?.objective, tags2);
      tags2 = this.getTagsByCategories(this.eventTags.value?.constraint, tags2);
      tags2 = this.getTagsByCategories(this.eventTags.value?.operation, tags2);
      tags2 = this.getTagsByCategories(this.eventTags.value?.targetPublic, tags2);
      tags2 = this.getTagsByCategories(this.eventTags.value?.participationBenefit, tags2);

      if (this.onSiteChecked && this.eventAvailableOnSitePlaces.value !== null && this.eventAvailableOnSitePlaces.value > 0) {
        this.pushModality(this.ONSITE_MODALITY_ID, this.eventAvailableOnSitePlaces.value, this.eventUrl.value);
      }

      if (this.remoteChecked && this.eventAvailableRemotePlaces.value !== null && this.eventAvailableRemotePlaces.value > 0) {
        this.pushModality(this.REMOTE_MODALITY_ID, this.eventAvailableRemotePlaces.value, this.eventUrl.value);
      }

      const payload = {
        dateEvenement: this.datepipe.transform(this.eventDate.value, 'dd-MM-yyyy'),
        description: this.eventDescription.value,
        deroulement: this.eventProgress.value,
        heureDebut: this.eventStartHour.value,
        heureFin: this.eventEndHour.value,
        estApublier: isPublished,
        etatId: stateId,
        adresse: this.eventAddress.value,
        codePostal: this.eventPostalCode.value?.toString(),
        ville: this.eventCity.value,
        nafCode: this.eventNafCode.value,
        timeZone: 'Europe/Paris', // ... provisoire
        titre: this.eventTitle.value,
        romeId: this.eventRomeId.value,
        modeAcceesList: this.modalityAccess,
        offres: this.eventOffers.value,
        prerequis: this.eventPrerequisites.value,
        tags: tags2,
        intervenants: this.eventRecruiters.value,
        lienPlusInformation: this.eventInformationLink.value,
        libelleLienPlusInformation: this.eventInformation.value,
        urlParticipation: this.eventUrl.value
      };

      try {
        if (stateId === this.DRAFT_ID) {
          if (this.creationMode) {
            this.isLoading = true;
            this.eventApiS.createEvent(payload).subscribe(event => {
              this.event = event;
              this.isLoading = false;
              this.router.navigate(['evenements']).then(() => {
                window.location.reload();
              });
            });
          } else if (this.editionMode) {
            this.isLoading = false;
            this.eventApiS.updateEvent(payload, this.eventToUpdate?.id).subscribe((data) => {
              this.event = data;
              this.isLoading = false;
              if ( this.event ) {
                this.router.navigate(['evenements']).then(() => {
                  window.location.reload();
                });
              }
            });
          }
        } else if (stateId === this.PUBLISHED_ID) {
          const confirmDialog = this.dialog.open(DialogConfirmationComponent, {
            data: {
              title: 'Visibilité de cet évènement',
              message: 'Souhaitez-vous rendre visible cet évènement auprès des candidats ?',
              buttonText: {
                ok: 'OUI',
                cancel: 'NON'
              }
            }
          });
          confirmDialog.afterClosed().subscribe(result => {
            if (this.creationMode) {
              payload.estApublier = result;
              this.isLoading = true;
              this.eventApiS.createEvent(payload).subscribe(
                data => {
                  this.isLoading = false;
                  this.event = data;
                  this.router.navigate(['evenements']).then(() => {
                    window.location.reload();
                  });
                },
                err => {
                  this.validateAllFormFieldsModality(this.createEventForm);
                  this.settingsControl = true;
                  this.isLoading = this.apiService.spinner;
                  });
            } else if (this.editionMode) {
              this.isLoading = true;
              payload.estApublier = result;
              this.event = this.eventApiS.updateEvent(payload, this.eventToUpdate?.id).subscribe(data => {
                this.event = data;
                this.isLoading = this.apiService.spinner;
                if (this.event) {
                  this.router.navigate(['evenements']).then(() => {
                    window.location.reload();
                  });
                }
              }, err => {
                this.validateAllFormFieldsModality(this.createEventForm);
                this.settingsControl = true;
                this.isLoading = this.apiService.spinner;
              });
            }
          });
        }
      } catch (error) {
        return error;
      }
    }
  }

  private handleIsOnSiteFields(checked: boolean): void {
    if (checked) {
      this.onSiteChecked = true;
      this.eventAvailableOnSitePlaces.enable();
    } else {
      this.eventAvailableOnSitePlaces.disable();
    }
  }

  private handleIsRemoteFields(checked: boolean): void {
    if (checked) {
      this.remoteChecked = true;
      this.eventAvailableRemotePlaces.enable();
      this.eventUrl.enable();
    } else {
      this.eventAvailableRemotePlaces.disable();
      this.eventUrl.disable();
    }
  }

  private validateAllFormFieldsModality(formGroup: FormGroup): boolean {
    this.validationState = true;
    this.displaySettings();
    return this.validationState;
  }

  private validateAllFormFields(formGroup: FormGroup): boolean {
    this.validateSettingsFields(formGroup);
    this.validateFeaturesFields(formGroup);
    this.validatePrerequisitesFields(formGroup);
    this.validateDetailsFields(formGroup);
    this.validateModalityFields(formGroup);
    if (!this.settingsControl && !this.featuresControl && !this.prerequisiteEventControl && !this.descriptionControl && !this.modalityControl) {
      this.validationState = false;
    } else {
      this.validationState = true;
    }
    return this.validationState;
  }

  private validateSettingsFields(formGroup: FormGroup): boolean {
    this.settingsControl = false;
    const requiredFields = ['titre', 'dateEvenement', 'heureDebut', 'heureFin'];
    requiredFields.forEach(field => {
      const control = formGroup.get(field);
      if (control instanceof FormControl) {
        if (requiredFields.indexOf(field) !== -1 && control.status === 'INVALID') {
          this.invalidateField(this.createEventForm, field);
          this.settingsControl = true;
        }
      }
    });
    if (formGroup.get('romeId')?.status === 'INVALID') {
      this.invalidateField(this.romeComponent?.romeForm, 'rome');
      this.settingsControl = true;
    }
    if (formGroup.get('nafCode')?.status === 'INVALID') {
      this.invalidateField(this.nafComponent?.nafForm, 'naf');
      this.settingsControl = true;
    }
    return this.settingsControl;
  }

  private validateFeaturesFields(formGroup: FormGroup): boolean {
    this.featuresControl = false;
    const control = formGroup.get('tags');
    if (control instanceof FormControl) {
      if (control.status === 'INVALID' || !this.checkTagsValues(control.value)) {
        this.invalidateField(this.tagsComponent?.tagsForm, 'targetPublic');
        this.invalidateField(this.tagsComponent?.tagsForm, 'eventType');
        this.invalidateField(this.tagsComponent?.tagsForm, 'objective');
        this.featuresControl = true;
      }
    }
    return this.featuresControl;
  }

  private validatePrerequisitesFields(formGroup: FormGroup): boolean {
    this.prerequisiteEventControl = false;
      const control = formGroup.get('prerequis');
      if (control instanceof FormControl) {
        if (control.status === 'INVALID' || this.checkPrerequisitesValues(control.value)) {
          this.prerequisiteEventControl = true;
        }
      }
    return this.prerequisiteEventControl;
  }

  private validateDetailsFields(formGroup: FormGroup): boolean {
    this.descriptionControl = false;
    Object.keys(formGroup.controls).forEach(field => {
      const control = formGroup.get(field);
      if (control instanceof FormControl) {
        if (field === 'description' && control.status === 'INVALID') {
          this.invalidateField(this.createEventForm, field);
          this.descriptionControl = true;
        }
      }
    });
    return this.descriptionControl;
  }

  private validatePartialFields(formGroup: FormGroup): boolean {
    Object.keys(formGroup.controls).forEach(field => {
      const control = formGroup.get(field);
      if (control instanceof FormControl) {
        if (field === 'titre' && control.status === 'INVALID') {
          this.validationDraftState = true;
          const title = formGroup.get('titre');
          if (title instanceof FormControl) {
            this.eventTitle.markAsTouched({onlySelf: true});
          }
        }
        if (field === 'dateEvenement' && control.status === 'INVALID') {
          this.validationDraftState = true;
          const title = formGroup.get('dateEvenement');
          if (title instanceof FormControl) {
            this.eventDate.markAsTouched({onlySelf: true});
          }
        } else if ((field === 'titre' && control.status === 'VALID') || (field === 'dateEvenement' && control.status === 'VALID')) {
          this.validationDraftState = false;
        }
      }
    });
    return this.validationDraftState;
  }

  private validateModalityFields(formGroup: FormGroup): boolean {
    this.modalityControl = false;
    if (!this.remoteChecked && !this.onSiteChecked) {
      if (this.eventOnSiteModality.status === 'INVALID' && this.eventRemoteModality.status === 'INVALID') {
        this.eventOnSiteModality.markAsTouched({onlySelf: true});
        this.eventAvailableOnSitePlaces.markAsTouched({onlySelf: true});
        this.eventAvailableRemotePlaces.markAsTouched({onlySelf: true});
        this.eventRemoteModality.markAsTouched({onlySelf: true});
        this.modalityControl = true;
        this.settingsControl = true;
      }
    } else {
      this.eventRemoteModality.setErrors({firstError: null});
      this.eventRemoteModality.updateValueAndValidity();
      this.eventOnSiteModality.markAsTouched({onlySelf: false});
      this.eventAvailableOnSitePlaces.markAsTouched({onlySelf: false});
      this.eventAvailableRemotePlaces.markAsTouched({onlySelf: false});
      this.eventRemoteModality.markAsTouched({onlySelf: false});
    }
    return this.modalityControl;
  }

  private invalidateField(formGroup: FormGroup, field: string): any {
    let markAsTouched;
    if (formGroup.get(field) !== undefined || formGroup.get(field) !== null) {
      markAsTouched = formGroup.get(field)?.markAsTouched({onlySelf: true});
    }
    return markAsTouched;
  }

  private setValueToUpdate(): void {
    this.eventPublished.setValue(this.eventToUpdate?.estApublier);
    this.eventTitle.setValue(this.eventToUpdate?.titre);
    this.eventDate.setValue(this.eventToUpdate?.dateISO);
    this.eventStartHour.setValue(this.eventToUpdate?.heureDebut);
    this.eventEndHour.setValue(this.eventToUpdate?.heureFin);
    this.eventAddress.setValue(this.eventToUpdate?.adresse);
    this.eventPostalCode.setValue(this.eventToUpdate?.codePostal);
    this.eventCity.setValue(this.eventToUpdate?.ville);
    this.eventRomeId.setValue(this.eventToUpdate?.romeId);
    this.eventNafCode.setValue(this.eventToUpdate?.nafCode);
    if (this.eventToUpdate !== undefined) {
      for (let modality of this.eventToUpdate.modeAcceesList) {
        let modalityAccessId = modality.modaliteAcces;
        if (Object.values(modality).includes(modalityAccessId)) {
          switch (modalityAccessId.id) {
            case 0:
              this.remoteChecked = true;
              this.eventRemoteModality.setValue(true);
              this.eventAvailableRemotePlaces.setValue(modality.nombrePlace);
              this.eventUrl.setValue(modality.urlParticipation);
              this.handleIsRemoteFields(true);
              break;
            case 1:
              this.onSiteChecked = true;
              this.eventOnSiteModality.setValue(true);
              this.eventAvailableOnSitePlaces.setValue(modality.nombrePlace);
              this.handleIsOnSiteFields(true);
              break;
            default:
              break;
          }
        }
      }
    }
    this.eventDescription.setValue(this.eventToUpdate?.description);
    this.eventProgress.setValue(this.eventToUpdate?.deroulement);
    this.eventInformationLink.setValue(this.eventToUpdate?.lienPlusInformation);
    this.eventInformation.setValue(this.eventToUpdate?.libelleLienPlusInformation);
    this.eventOffers.setValue(this.eventToUpdate?.offres);
    this.eventRecruiters.setValue(this.eventToUpdate?.intervenants);
    this.eventPrerequisites.setValue(this.eventToUpdate?.prerequis);
    const tagsByTarget: any[] = [];
    const tagsByBenefit: any[] = [];
    let tagsByType;
    const tagsByObjective: any[] = [];
    const tagsByOperation: any[] = [];

    if (this.eventToUpdate !== undefined) {
     for (let tag of this.eventToUpdate.tags) {
       let typeTagId = tag.typeTagId;
       if (Object.values(tag).includes(typeTagId)) {
         switch (typeTagId) {
           case 0:
            tagsByTarget.push(tag.id);
            break;
            case 1:
              tagsByBenefit.push(tag.id);
            break;
            case 2:
              tagsByType = tag.id;
            break;
            case 3:
              tagsByObjective.push(tag.id);
            break;
            case 4:
              tagsByOperation.push(tag.id);
            break;
           default:
             break;
         }
       }
     }
    }

    const tags = {
      targetPublic: tagsByTarget,
      participationBenefit: tagsByBenefit,
      eventType: tagsByType,
      objective: tagsByObjective,
      operation: tagsByOperation
    };
    this.eventTags.setValue(tags);
  }

  public disableWeekend(d: any): any {
    if (d?.getDay() !== 0 && d?.getDay() !== 6) {
      return d;
    }
  }

  public setDescriptionControl(e: any): void {
    this.descriptionControl = false;
  }

  public setSettingsControl(e: any): void {
    if (
      this.isTitleValid && this.isDateValid && this.isStartHourValid && this.isStartEndValid &&
      this.isRomeIdValid && this.isRomeIdValid && this.isNafCodeValid && this.isModalityValid) {
      this.settingsControl = false;
    }
  }

  public isTitleValid(): boolean {
    return this.eventTitle.value !== null && this.eventTitle.value !== '';
  }

  public isDateValid(): boolean {
    return this.eventDate !== undefined && this.eventDate.value !== null;
  }

  public isStartHourValid(): boolean {
    return this.eventStartHour.value !== null;
  }

  public isStartEndValid(): boolean {
    return this.eventEndHour.value !== null;
  }

  public isRomeIdValid(): boolean {
    return this.eventRomeId !== null;
  }

  public isNafCodeValid(): boolean {
    return this.eventNafCode !== null;
  }

  public isModalityValid(): boolean {
    return this.remoteChecked && this.onSiteChecked;
  }

  private checkTagsValues(tags: { targetPublic: Array<ITag>, objective: Array<ITag>, eventType: Array<ITag>}): boolean {
    return tags.targetPublic && tags.targetPublic.length > 0
      && tags.objective && tags.objective.length > 0
    && tags.eventType && tags.eventType.length !== null
  }

  private checkPrerequisitesValues(prerequisites: Array<any>) {
    return !prerequisites || prerequisites.length < 2;
  }

  ngOnDestroy(): void {
    this.unsubscribe.next();
  }

}
