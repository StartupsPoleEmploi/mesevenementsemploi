import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';

import {PrerequisitesService} from '../../../../shared/services/prerequisites.service';
import {MatCheckboxChange} from '@angular/material/checkbox';

@Component({
  selector: 'app-prerequisites',
  templateUrl: './prerequisites.component.html',
  styleUrls: ['./prerequisites.component.scss']
})
export class PrerequisitesComponent implements OnInit {

  @Output() newPrerequisitesEvent = new EventEmitter<any[]>();
  @Output() prerequisiteEventControl = new EventEmitter<boolean>();
  @Input() validationState: boolean | undefined;

  public prerequisitesCategoriesList: any;
  public prerequisitesByDiploma: any;
  public prerequisitesByAge: any;
  public prerequisitesByLicence: any;
  public prerequisitesBySoftSkill: any;
  public prerequisitesByConstraint: any;
  public prerequisites = new Array<{ id: string }>();
  public selectedAge: any;

  public selected = -1;

  public isDiploma = true;
  public isAgeAndLicence = false;
  public isSkills = false;
  public isConstraint = false;

  public DIPLOME_CATEGORY_ID = 0;
  public AGE_CATEGORY_ID = 1;
  public LICENCE_CATEGORY_ID = 2;
  public SKILLS_CATEGORY_ID = 3;
  public CONSTRAINT_CATEGORY_ID = 4;

  constructor(private prerequisitesS: PrerequisitesService) {
  }

  ngOnInit(): void {
    this.prerequisitesS.getAllPrerequisitesCategories().subscribe(datas => {
      this.prerequisitesCategoriesList = datas;
    });
    this.prerequisitesS.getAllPrerequisitesById(this.DIPLOME_CATEGORY_ID).subscribe(datas => {
      this.prerequisitesByDiploma = datas;
    });
    this.prerequisitesS.getAllPrerequisitesById(this.AGE_CATEGORY_ID).subscribe(datas => {
      this.prerequisitesByAge = datas;
    });
    this.prerequisitesS.getAllPrerequisitesById(this.LICENCE_CATEGORY_ID).subscribe(datas => {
      this.prerequisitesByLicence = datas;
    });
    this.prerequisitesS.getAllPrerequisitesById(this.SKILLS_CATEGORY_ID).subscribe(datas => {
      this.prerequisitesBySoftSkill = datas;
    });
    this.prerequisitesS.getAllPrerequisitesById(this.CONSTRAINT_CATEGORY_ID).subscribe(datas => {
      this.prerequisitesByConstraint = datas;
    });
  }

  public onChange(event: MatCheckboxChange): void {
    this.checkStatus(event);
    this.setPrerequisitesControl();
  }

  public displayDiploma(): void {
    this.isDiploma = true;
    this.isAgeAndLicence = false;
    this.isSkills = false;
    this.isConstraint = false;
  }

  public displayAgeAndLicence(): void {
    this.isDiploma = false;
    this.isAgeAndLicence = true;
    this.isSkills = false;
    this.isConstraint = false;
  }

  public displaySkills(): void {
    this.isDiploma = false;
    this.isAgeAndLicence = false;
    this.isSkills = true;
    this.isConstraint = false;
  }

  public displayConstraint(): void {
    this.isDiploma = false;
    this.isAgeAndLicence = false;
    this.isSkills = false;
    this.isConstraint = true;
  }

  public setValue(prerequis: any): void {
    this.prerequisites = prerequis;
  }

  public isSelected(prerequisToSelect: string): boolean {
    const index = this.prerequisites.findIndex(item => item.id === prerequisToSelect);
    return index !== -1;
  }

  private checkStatus(event: MatCheckboxChange): void {
    if (event.checked === false) {
      const index = this.prerequisites.findIndex(elmt => elmt.id === event.source.value);
      this.prerequisites.splice(index, 1);
    } else {
      this.prerequisites.push({id: event.source.value});
    }
    this.newPrerequisitesEvent.emit(this.prerequisites);
  }

  private setPrerequisitesControl() {
    if(this.prerequisites !== undefined && this.prerequisites.length >= 2) {
      this.validationState = false;
      this.prerequisiteEventControl.emit(false);
    }
  }

  public onChangeAge(event: MatCheckboxChange): void {
    this.checkStatus(event);
    this.validationState = false;
    this.prerequisiteEventControl.emit(false);
  }

}
