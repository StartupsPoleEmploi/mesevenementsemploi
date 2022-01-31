import {Component, ErrorHandler, EventEmitter, OnInit, Output} from '@angular/core';
import {RomeService} from '../../../../shared/services/rome.service';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {TokenStorageService} from '../../../../shared/services/token-storage.service';
import {MatSnackBar} from "@angular/material/snack-bar";


@Component({
  selector: 'app-rome',
  templateUrl: './rome.component.html',
  styleUrls: ['./rome.component.scss']
})
export class RomeComponent implements OnInit {

  @Output() newRomeEvent = new EventEmitter<any>();

  public romeList: any;
  public romeForm: any;
  selected: any;

  constructor(errorHandler: ErrorHandler, tokenStorage: TokenStorageService, snackBar: MatSnackBar) {
    const romeS = RomeService.getInstance(errorHandler, tokenStorage, snackBar);
    romeS.romeListSelectedFromMap
      .subscribe(romesFromMap => this.romeList = romesFromMap);
  }

  ngOnInit(): void {
    this.romeForm = new FormGroup({
      rome: new FormControl(
        null,
        [Validators.required]
      )
    });
  }

  public onChangeValue(codeSelected: string): void {
    this.newRomeEvent.emit(codeSelected);
  }

  public setValue(code: string): void {
    this.romeForm.get('rome').patchValue(code);
  }

}
