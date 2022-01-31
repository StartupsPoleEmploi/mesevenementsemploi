import {Injectable} from '@angular/core';
import {BehaviorSubject, Observable} from 'rxjs';

@Injectable()
export class UrlService {
  private previousUrl: BehaviorSubject<string> = new BehaviorSubject<string>('');
  public previousUrl$: Observable<string> = this.previousUrl.asObservable();
  public shareUrl: string | undefined;

  constructor() {
  }

  setPreviousUrl(previousUrl: string) {
    this.previousUrl.next(previousUrl);
  }

  public eventUrlToShare(eventId: number | undefined): string {
    this.shareUrl = `${window.location.origin}/evenement/${eventId}`;
    return this.shareUrl;
  }


}
