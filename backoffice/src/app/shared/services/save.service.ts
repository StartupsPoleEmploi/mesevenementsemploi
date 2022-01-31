import {Injectable} from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class SaveService {

  public savedValues: any;

  public save(formValue: any): void {
    this.savedValues = formValue;
  }

}
