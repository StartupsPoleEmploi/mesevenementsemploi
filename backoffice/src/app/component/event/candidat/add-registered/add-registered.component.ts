import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {IEvent} from '../../../../model/IEvent';
import {Location} from '@angular/common';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {MatSelectChange} from '@angular/material/select';
import {CandidatService} from '../../../../shared/services/candidat.service';
import {ActivatedRoute, Router} from '@angular/router';
import {EventApiService} from '../../../../shared/services/event-api.service';
import {ModalityAccessService} from '../../../../shared/services/modality-access.service';
import {NavbarService} from "../../../../shared/services/navbar.service";

@Component({
  selector: 'app-add-registered',
  templateUrl: './add-registered.component.html',
  styleUrls: ['./add-registered.component.scss']
})

export class AddRegisteredComponent implements OnInit, OnDestroy {
  @Input() event: IEvent | undefined;
  eventId: number | undefined;
  modaliteId: number | undefined;
  public addCandidatForm: any;
  days = new Array(31);
  public validationForm = false;
  public candidatDejaInscrit = false;
  public eventComplet = false;
  public isName = false;
  public isMonth = false;
  public isYear = false;
  public isPrenom = false;
  public isIdRci = false;
  public isDateNaissance = false;
  public isDay = false;
  public isEmail = false;
  public isCivilite = false;
  public isModaliteAccesId = false;
  public settingsControl = false;
  candidat = [];
  selectedData: any;
  years = [];
  year: any;
  month: any;
  day: any;
  months = [
    {id: '01', name: 'Jan'},
    {id: '02', name: 'Fev'},
    {id: '03', name: 'Mar'},
    {id: '04', name: 'Avr'},
    {id: '05', name: 'Mai'},
    {id: '06', name: 'Jui'},
    {id: '07', name: 'Juil'},
    {id: '08', name: 'Aou'},
    {id: '09', name: 'Sep'},
    {id: '10', name: 'Oct'},
    {id: '11', name: 'Nov'},
    {id: '12', name: 'Dec'}
  ];
  private sub: any;

  constructor(private location: Location,
              private route: ActivatedRoute,
              private candidatService: CandidatService,
              private eventService: EventApiService,
              private modaliteAccesService: ModalityAccessService,
              private navbarService: NavbarService,
              private router: Router) {
    this.navbarService.show();
    this.navbarService.handleTabs(
      true,
      'Inscrire un candidat',
      false,
      false,
      false,
      false,
      true,
      true,
      false,
      false,
    );
    const max = new Date().getFullYear();
    const min = max - 100;
    for (let i = max; i >= min; i--) {
      // @ts-ignore
      this.years.push(i);
    }
  }

  ngOnInit(): void {
    this.sub = this.route.params.subscribe(params => {
      this.eventId = +params.eventId;
    });

    this.eventService.getEventById(this.eventId).subscribe(event => {
      this.event = event;
    });

    this.modaliteAccesService.getAllModaliteAccessByEventId(this.eventId).subscribe(datas => {
      datas.forEach((modalityAccess: any) => {
        return this.modaliteId = modalityAccess?.modaliteAcces?.id;
      });
    });

    this.createCandidatForm();
  }

  ngOnDestroy(): void {
    this.sub.unsubscribe();
  }

  public get candidatEmail(): FormControl {
    return this.addCandidatForm.get('email');
  }

  public get candidatNom(): FormControl {
    return this.addCandidatForm.get('nom');
  }

  public get candidatPrenom(): FormControl {
    return this.addCandidatForm.get('prenom');
  }

  public get candidatIdRci(): FormControl {
    return this.addCandidatForm.get('identifiantRci');
  }

  public get candidatCivilte(): FormControl {
    return this.addCandidatForm.get('civilte');
  }

  public get candidatModalite(): FormControl {
    return this.addCandidatForm.get('modaliteAccesId');
  }

  public get candidatDateNaissance(): string {
    return this.day + '-' + this.month + '-' + this.year;
  }

  public get candidatDay(): FormControl {
    return this.addCandidatForm.get('day');
  }

  public get candidatMonth(): FormControl {
    return this.addCandidatForm.get('month');
  }

  public get candidatYear(): FormControl {
    return this.addCandidatForm.get('year');
  }

  public candidatM(): number {
    return this.modaliteId as number;
  }

  public backClicked(): void {
    this.location.back();
  }

  public numericOnly(event: any): boolean {
    const patt = /^([0-9])$/;
    return patt.test(event.key);
  }

  public ValidateFormCandidatDejaInscrit(formGroup: FormGroup): boolean {
    this.candidatDejaInscrit = true;
    this.displayFields();
    return this.candidatDejaInscrit;
  }

  public ValidateFormEventComplet(formGroup: FormGroup): boolean {
    this.eventComplet = true;
    this.displayFields();
    return this.candidatDejaInscrit;
  }

  selectedMonthValue(event: MatSelectChange): void {
    this.selectedData = {
      value: event.value,
      text: event.source.triggerValue
    };
    this.month = this.selectedData.text;
  }

  selectedDayValue(event: MatSelectChange): void {
    this.selectedData = {
      value: event.value,
      text: event.source.triggerValue
    };
    this.day = this.selectedData.text;
  }

  selectedYearValue(event: MatSelectChange): void {
    this.selectedData = {
      value: event.value,
      text: event.source.triggerValue
    };
    this.year = this.selectedData.text;
  }

  public createCandidatForm(): void {
    this.addCandidatForm = new FormGroup({
      email: new FormControl(null, [Validators.required, Validators.email, Validators.pattern('^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$')]),
      nom: new FormControl(null, [Validators.required]),
      prenom: new FormControl(null, [Validators.required]),
      identifiantRci: new FormControl(null, [Validators.required]),
      modaliteAccesId: new FormControl(null, [Validators.required]),
      civilte: new FormControl(null, [Validators.required]),
      dateNaissance: new FormControl(null, [Validators.required]),
      day: new FormControl(null, [Validators.required]),
      month: new FormControl(null, [Validators.required]),
      year: new FormControl(null, [Validators.required]),
    });
  }

  public onSubmit(): void {
    // tslint:disable-next-line:variable-name
    const NewRegistered = {
      email: this.candidatEmail.value,
      nom: this.candidatNom.value,
      prenom: this.candidatPrenom.value,
      identifiantRci: this.candidatIdRci.value,
      civilite: this.candidatCivilte.value,
      modaliteAccesId: parseInt(this.candidatModalite.value),
      dateNaissance: this.candidatDateNaissance,
      statutInscription: 0
    };

    if (this.event?.modeAcceesList.length === 1) {
      if (this.modaliteId != null) {
        NewRegistered.modaliteAccesId = this.modaliteId;
      }
    }
    // @ts-ignore
    this.candidat.push(NewRegistered);
    if (this.isEmailValid(this.candidatEmail.value)) {
      this.candidatService.inscrireCandidat(NewRegistered, this.eventId).subscribe(data => {
        this.router.navigate(['candidats/' + this.eventId]);
      }, err => {
        this.settingsControl = true;
        if (err.code == 403) {
          this.ValidateFormEventComplet(this.addCandidatForm);
        }
        if (err.code == 409) {
          this.ValidateFormCandidatDejaInscrit(this.addCandidatForm);
        } else {
          this.validateAllFormFields(this.addCandidatForm);
        }
      });
    } else {
      this.validateAllFormFields(this.addCandidatForm);
    }
  }

  public saveCandidat(): void {
    // tslint:disable-next-line:forin
    this.candidatNom.markAsTouched({onlySelf: true});
    this.candidatPrenom.markAsTouched({onlySelf: true});
    this.candidatEmail.markAsTouched({onlySelf: true});
    this.candidatIdRci.markAsTouched({onlySelf: true});
    this.candidatDay.markAsTouched({onlySelf: true});
    this.candidatMonth.markAsTouched({onlySelf: true});
    this.candidatYear.markAsTouched({onlySelf: true});
    this.candidatCivilte.markAsTouched({onlySelf: true});
    this.candidatModalite.markAsTouched({onlySelf: true});

    this.onSubmit();
  }

  public checkError = (controlName: string, errorName: string) => {
    return this.addCandidatForm.controls[controlName].hasError(errorName);
  }

  public displayFields(): void {
    this.isName = true;
    this.isPrenom = true;
    this.isIdRci = true;
    this.isDateNaissance = true;
    this.isDay = true;
    this.isMonth = true;
    this.isYear = true;
    this.isEmail = true;
    this.isCivilite = true;
    this.isModaliteAccesId = true;
  }

  private validateAllFormFields(formGroup: FormGroup): boolean {
    this.validationForm = true;
    this.displayFields();
    return this.validationForm;
  }

  public setSettingsControl(e: any): void {
    if (this.isEmailValid(this.candidatEmail.value) && this.isPrenomValid() && this.isNomValid()) {
      this.settingsControl = false;
    }
  }

  public saveEmail(): void {
    if (this.isEmailValid(this.candidatEmail.value)) {
      this.router.navigate(['candidats/' + this.eventId]);
    } else {
      this.validateAllFormFields(this.addCandidatForm);
    }
  }

  public isEmailValid(str: any): boolean {
    const pattern = /^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$/;
    return pattern.test(str);
  }

  public isPrenomValid(): boolean {
    return this.candidatPrenom.value !== null && this.candidatPrenom.value !== '';
  }

  public isNomValid(): boolean {
    return this.candidatNom.value !== null && this.candidatNom.value !== '';
  }
}
