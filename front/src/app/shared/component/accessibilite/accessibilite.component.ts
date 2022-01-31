import {BreakpointObserver} from '@angular/cdk/layout';
import {Component, OnInit} from '@angular/core';
import {Observable} from 'rxjs';
import {map} from 'rxjs/operators';

@Component({
  selector: 'app-accessibilite',
  templateUrl: './accessibilite.component.html',
  styleUrls: ['./accessibilite.component.scss']
})
export class AccessibiliteComponent implements OnInit {

  public isSmallScreen: Observable<boolean>;
  public isWideScreen: Observable<boolean>;

  constructor(private breakpointObserver: BreakpointObserver) {
    this.isWideScreen = this.breakpointObserver
        .observe(['(min-width: 821px)'])
        .pipe(map(({matches}) => matches));
    this.isSmallScreen = this.breakpointObserver
        .observe(['(max-width: 820px)'])
        .pipe(map(({matches}) => matches));
  }

  ngOnInit(): void {
  }

}
