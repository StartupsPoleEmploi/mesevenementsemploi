import {Component, Input, OnInit} from '@angular/core';
import {IEvent} from '../../../model/IEvent';
import {ActivatedRoute, Router} from '@angular/router';
import {EventApiService} from '../../../shared/services/event-api.service';
import {UrlService} from '../../../shared/services/url.service';
import {Observable} from 'rxjs';
import {RoutesEnum} from '../../../model/routes.enum';
import {IsRegisteredService} from '../../../shared/services/is-registered.service';

@Component({
  selector: 'app-inscription-sur-place',
  templateUrl: './inscription-sur-place.component.html',
  styleUrls: ['./inscription-sur-place.component.scss']
})
export class InscriptionSurPlaceComponent implements OnInit {

  @Input() event: IEvent | undefined;

  public shareUrl: string | undefined;
  private previousUrl: Observable<string> = this.urlService.previousUrl$;
  private candidateRegistrationStatus: boolean | undefined;

  public eventId: number | undefined;
  public currentEvent: IEvent | undefined;
  private originUrl = '';

  constructor(
      private route: ActivatedRoute,
      private eventService: EventApiService,
      private urlService: UrlService,
      private router: Router,
      private isRegisteredS: IsRegisteredService,
  ) {
  }

  ngOnInit(): void {
    this.isRegisteredS.isCandidateRegistered.subscribe(status => {
      this.candidateRegistrationStatus = status;
    });
    this.previousUrl.subscribe(url => {
      this.originUrl = url;
    });
    if (this.originUrl !== '/prerequis' && !this.candidateRegistrationStatus) {
      this.router.navigate([RoutesEnum.EVENTS]);
    }
    const eventId = localStorage.getItem('eventId');
    this.eventId = eventId as unknown as number;
    this.eventService.getEventById(this.eventId).subscribe(datas => {
      this.currentEvent = datas;
      if (this.currentEvent !== undefined) {
        this.event = this.currentEvent;
      }
      return;
    });
    this.shareUrl = this.urlService.eventUrlToShare(this.currentEvent?.id);
  }

}
