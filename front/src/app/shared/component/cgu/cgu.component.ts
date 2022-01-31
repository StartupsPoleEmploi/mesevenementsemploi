import {Component, OnInit} from '@angular/core';
import {Location} from '@angular/common';
import {Router} from '@angular/router';
import {BreakpointObserver} from '@angular/cdk/layout';
import {Observable} from 'rxjs';
import {map} from 'rxjs/operators';

@Component({
  selector: 'app-cgu',
  templateUrl: './cgu.component.html',
  styleUrls: ['./cgu.component.scss']
})
export class CguComponent implements OnInit {

  public isSmallScreen: Observable<boolean>;
  public isWideScreen: Observable<boolean>;

  constructor(private location: Location,
              private router: Router,
              private breakpointObserver: BreakpointObserver) {
    this.isWideScreen = this.breakpointObserver
      .observe(['(min-width: 821px)'])
      .pipe(map(({matches}) => matches));
    this.isSmallScreen = this.breakpointObserver
      .observe(['(max-width: 820px)'])
      .pipe(map(({matches}) => matches));
  }

  ngOnInit(): void {
  }

  public backClicked(): void {
    this.location.back();
  }

  public backHome(): void {
    this.router.navigate(['evenements']);
  }

}
