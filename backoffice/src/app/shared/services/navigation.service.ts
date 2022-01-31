import {Injectable} from '@angular/core';
import {NavigationEnd, Router} from '@angular/router';
import {Location} from '@angular/common';
import {BehaviorSubject} from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class NavigationService {

  private history: string[] = [];
  private mapNumber = new BehaviorSubject(1);
  public numberFromMap = this.mapNumber.asObservable();

  constructor(private router: Router, private location: Location) {
    this.router.events.subscribe((event) => {
      if (event instanceof NavigationEnd) {
        this.history.push(event.urlAfterRedirects);
      }
    });
  }

  back(): void {
    this.history.pop();
    if (this.history.length > 0) {
      this.location.back();
    } else {
      this.router.navigateByUrl('/');
    }
  }

  updatePageNumber(pageNumber: number): void {
    this.mapNumber.next(pageNumber);
  }
}
