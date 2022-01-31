import {Component, OnInit} from '@angular/core';
import {Observable} from "rxjs";
import {UrlService} from "../../../shared/services/url.service";
import {Router} from "@angular/router";
import {RoutesEnum} from "../../../model/routes.enum";

@Component({
  selector: 'app-candidat-deja-inscrit',
  templateUrl: './candidat-deja-inscrit.component.html',
  styleUrls: ['./candidat-deja-inscrit.component.scss']
})
export class CandidatDejaInscritComponent implements OnInit {

  private originUrl: string = '';
  private previousUrl: Observable<string> = this.urlService.previousUrl$;

  constructor(
      private urlService: UrlService,
      private router: Router,
      ) { }

  ngOnInit(): void {

    this.previousUrl.subscribe(url => {
      this.originUrl = url;
    });
    if (this.originUrl == '/evenement/:eventId') {
      this.router.navigate([RoutesEnum.EVENTS]);
    }

  }

}
