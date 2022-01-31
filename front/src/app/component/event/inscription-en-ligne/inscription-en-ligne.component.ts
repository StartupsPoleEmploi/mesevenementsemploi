import {Component, Input, OnInit} from '@angular/core';
import {IEvent} from '../../../model/IEvent';
import {ActivatedRoute, Router} from '@angular/router';
import {EventApiService} from '../../../shared/services/event-api.service';
import {Observable} from 'rxjs';
import {UrlService} from '../../../shared/services/url.service';
import {RoutesEnum} from '../../../model/routes.enum';
import {IsRegisteredService} from '../../../shared/services/is-registered.service';

@Component({
  selector: 'app-inscription-en-ligne',
  templateUrl: './inscription-en-ligne.component.html',
  styleUrls: ['./inscription-en-ligne.component.scss']
})
export class InscriptionEnLigneComponent implements OnInit {

  @Input() event: IEvent | undefined;

  public shareUrl: string | undefined;
  private previousUrl: Observable<string> = this.urlService.previousUrl$;
  private candidateRegistrationStatus: boolean | undefined;
  public eventId: number | undefined;
  public currentEvent: IEvent | undefined;
  private originUrl = '';
  private typeEventId = 2;

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
    this.getEvent();
    this.shareUrl = this.urlService.eventUrlToShare(this.currentEvent?.id);

  }

  private getEvent() {
    const eventId = localStorage.getItem('eventId');
    this.eventId = eventId as unknown as number;
    this.eventService.getEventById(this.eventId).subscribe(datas => {
      this.currentEvent = datas;
      if (this.currentEvent !== undefined) {
        this.event = this.currentEvent;
      }
      return;
    });
  }

  public getTypeEvent(event: IEvent) {
    return event.tags.filter(tag => {
      return tag.typeTagId === this.typeEventId;
    });
  }

  public shareWithTwitter(): void {
    this.router.navigate([]).then(result => {
      window.open(`https://twitter.com/share?url=https://${this.shareUrl}`, '_blank')
    });
  }

  public shareWithLinkedin(): void {
    this.router.navigate([]).then(result => {
      window.open(`https://www.linkedin.com/shareArticle?mini=true&amp;url=${this.shareUrl}`, '_blank')
    });
  }

  public shareWithFacebook(): void {
    this.router.navigate([]).then(result => {
      window.open(`https://www.facebook.com/sharer.php?u=${this.shareUrl}`, '_blank')
    });
  }
}
