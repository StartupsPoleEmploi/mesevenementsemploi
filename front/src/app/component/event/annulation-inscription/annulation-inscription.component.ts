import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {environment} from '../../../../environments/environment';
import {PeConnectService} from '../../../shared/services/pe-connect.service';
import {RoutesEnum} from '../../../model/routes.enum';
import {CandidatConnectedService} from '../../../shared/services/candidat-connected.service';

@Component({
  selector: 'app-annulation-inscription',
  templateUrl: './annulation-inscription.component.html',
  styleUrls: ['./annulation-inscription.component.sass']
})
export class AnnulationInscriptionComponent implements OnInit {
  private eventId: number | undefined;

  constructor(private router: Router,
              private route: ActivatedRoute,
              private peConnectService: PeConnectService,
              private candidatConnectedS: CandidatConnectedService) {
  }

  ngOnInit(): void {
    let eventId;
    this.route.params.subscribe(params => {
      eventId = +params.eventId;
      localStorage.setItem('eventId', eventId as unknown as string);
    });
    this.peConnectService.login(`${environment.urlAcces}${RoutesEnum.DETAILS}`);
  }
}
