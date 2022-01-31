import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {ControlContainer, FormControl, FormGroup, Validators} from "@angular/forms";
import {DomSanitizer, SafeUrl} from "@angular/platform-browser";

import {ToastService} from '../../../../shared/services/toast.service';
import IRecruiter from '../../../../model/IRecruiters';

@Component({
  selector: 'app-recruiters',
  templateUrl: './recruiters.component.html',
  styleUrls: ['./recruiters.component.scss']
})
export class RecruitersComponent implements OnInit {

  @Output() newRecruiterEvent = new EventEmitter<IRecruiter[]>();

  public recruiterUrl = '';
  public theoreticallySafeUrl: SafeUrl | undefined;
  public recruiterForm: any;
  public headers = ['Nom', 'Site web'];
  public recruiters = new Array<IRecruiter>();
  public recruiterAdded = false;

  constructor(private sanitizer: DomSanitizer, private toastS: ToastService, private controlContainer: ControlContainer) {
  }

  ngOnInit(): void {
    this.recruiterForm = this.initForm();
    this.setTheoreticallySafeUrl();
  }

  private setTheoreticallySafeUrl(): void {
    this.theoreticallySafeUrl = this.sanitizer.bypassSecurityTrustUrl(this.recruiterUrl);
  }

  public initForm() {
    // const valideUrl = /https?:\/\/(www\.)?[-a-zA-Z0-9@:%._\+~#=]{2,256}\.[a-z]{2,4}\b([-a-zA-Z0-9@:%_\+.~#?&//=]*)/g;
    return new FormGroup({
      recruiterName: new FormControl(null, [Validators.required]),
      recruiterUrl: new FormControl(null),
    });
  }

  public addRecruiter(recruiterForm: any, formDirective: any) {
    if (this.recruiterForm.get('recruiterName').value !== null && !this.handleMaxSize()) {
      this.recruiters.push({
        libelle: this.recruiterForm.get('recruiterName').value,
        siteWeb: this.recruiterForm.get('recruiterUrl').value
      });
      this.recruiterAdded = true;
      recruiterForm.reset();
      formDirective.resetForm();
      this.newRecruiterEvent.emit(this.recruiters);
    } else {
      this.recruiterForm.get('recruiterName').markAsTouched({onlySelf: true});
    }
  }

  private handleMaxSize(): boolean {
    if (this.recruiters.length >= 4) {
      this.toastS.showToast('Le nombre de recruteur(s) est limité à 4', 'toast-info');
      this.recruiterForm =  this.initForm();
      return true;
    }
    return false;
  }

  public deleteRecruiter(recruiter: IRecruiter) {
    const index = this.recruiters.indexOf(recruiter);
    this.recruiters.splice(index, 1);
    this.recruiterForm = this.initForm();
  }

  public isRecruiters(): boolean {
    return this.recruiters && this.recruiters.length > 0;
  }

  public setValue(recruiters: any): void {
    this.recruiters = recruiters;
  }

}
