import {Component, Input, OnInit} from '@angular/core';
import {IEvent} from '../../../model/IEvent';
import {ActivatedRoute, Router} from '@angular/router';
import {EventApiService} from '../../../shared/services/event-api.service';
import {Observable} from "rxjs";
import {UrlService} from "../../../shared/services/url.service";
import {RoutesEnum} from "../../../model/routes.enum";

@Component({
  selector: 'app-places-non-disponibles',
  templateUrl: './places-non-disponibles.component.html',
  styleUrls: ['./places-non-disponibles.component.scss']
})
export class PlacesNonDisponiblesComponent implements OnInit {

  @Input() event: IEvent | undefined;

  private previousUrl: Observable<string> = this.urlService.previousUrl$;
  private originUrl: string = '';

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
    if (this.originUrl == '/prerequis') {
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
  }
}
