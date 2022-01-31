import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {NafService} from '../../../../shared/services/naf.service';
import {FormControl, FormGroup, Validators} from '@angular/forms';

@Component({
  selector: 'app-naf',
  templateUrl: './naf.component.html',
  styleUrls: ['./naf.component.scss']
})
export class NafComponent implements OnInit {

  @Output() newNafEvent = new EventEmitter<any>();

  public nafList: any;
  public nafForm: any;

  constructor(private nafS: NafService) {
    this.nafS.getNafList().subscribe(datas => {
      this.nafList = datas;
    });
  }

  ngOnInit(): void {
    this.nafForm = new FormGroup({
      naf: new FormControl(null, [Validators.required])
    });
  }

  public onChangeValue(nafSelected: string): void {
    this.newNafEvent.emit(nafSelected);
  }

  public setValue(code: string): void {
    this.nafForm.get('naf').patchValue(code);
  }
}
