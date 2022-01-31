import {Component, Input, OnInit} from '@angular/core';
import {IEvent} from '../../../model/IEvent';
import {ActivatedRoute, Router} from '@angular/router';
import {EventApiService} from '../../../shared/services/event-api.service';
import {Observable} from "rxjs";
import {UrlService} from "../../../shared/services/url.service";
import {RoutesEnum} from "../../../model/routes.enum";

@Component({
  selector: 'app-inscription-ko',
  templateUrl: './inscription-ko.component.html',
  styleUrls: ['./inscription-ko.component.scss']
})
export class InscriptionKoComponent implements OnInit {

  @Input() event: IEvent | undefined;

  private originUrl: string = '';
  private previousUrl: Observable<string> = this.urlService.previousUrl$;
  private sub: any;

  public eventId: number | undefined;
  public currentEvent: IEvent | undefined;

  constructor(
      private route: ActivatedRoute,
      private eventService: EventApiService,
      private urlService: UrlService,
      private router: Router,
      ) {
  }

  ngOnInit(): void {

    this.previousUrl.subscribe(url => {
      this.originUrl = url;
    });
    if (this.originUrl == '/evenement/:eventId') {
      this.router.navigate([RoutesEnum.EVENTS]);
    }

    this.sub = this.route.params.subscribe(params => {
      this.eventId = +params.eventId;
    });

    this.eventService.getEventById(this.eventId).subscribe(datas => {
      this.currentEvent = datas;
      if (this.currentEvent !== undefined) {
        this.event = this.currentEvent;
      }
      return;
    });
  }
}
