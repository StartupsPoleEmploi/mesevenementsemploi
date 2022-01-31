import {Component, OnInit} from '@angular/core';
import {environment} from '../../../../environments/environment';

@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html'
})
export class FooterComponent implements OnInit {
  public contactEmail: string = environment.emailContact;

  constructor() {
  }

  ngOnInit(): void {
  }

}
