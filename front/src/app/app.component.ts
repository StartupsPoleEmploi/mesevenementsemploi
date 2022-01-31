import {Component, OnInit} from '@angular/core';
import {environment} from '../environments/environment';
import {NavigationEnd, Router} from "@angular/router";
import {filter} from "rxjs/operators";
import {UrlService} from "./shared/services/url.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html'
})
export class AppComponent implements OnInit {
    title = 'pe-actions-candidat ';
    mail = environment.emailContact;
    previousUrl: string = '';
    currentUrl: string = '';

  constructor(private router: Router,
              private urlService: UrlService) {}

  ngOnInit() {
      this.router.events.pipe(
          filter((event): event is NavigationEnd => event instanceof NavigationEnd)
      ).subscribe((event: NavigationEnd) => {
          this.previousUrl = this.currentUrl;
          this.currentUrl = event.url;
          this.urlService.setPreviousUrl(this.previousUrl);
      });
  }

}
