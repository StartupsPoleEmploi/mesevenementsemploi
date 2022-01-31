import {ActivatedRoute, ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot,} from '@angular/router';
import {RoutesEnum} from '../../model/routes.enum';
import {Injectable} from '@angular/core';
import {CandidatConnectedService} from '../services/candidat-connected.service';
import {UrlService} from '../services/url.service';
import {Observable} from 'rxjs';
import {PeConnectService} from '../services/pe-connect.service';
import {environment} from '../../../environments/environment';

@Injectable()
export class AuthGuard implements CanActivate {

    private route: ActivatedRouteSnapshot;
    // public href: string = '';
    previousUrl: Observable<string> = this.urlService.previousUrl$;
    originUrl = '';
    private eventId: number | undefined;

    constructor(
        private activatedRoute: ActivatedRoute,
        private router: Router,
        private candidatConnectedS: CandidatConnectedService,
        private urlService: UrlService,
        private peConnectService: PeConnectService
    ) {
        this.route = activatedRoute.snapshot;
        this.previousUrl.subscribe(data => {
            // console.log('P.R.E.V.I.O.U.S. GUARD : ', data);
            return this.originUrl = data;
        });
        this.eventId = localStorage.getItem('eventId') as unknown as number;
    }

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
        localStorage.setItem('routerStateUrl', state.url);
        const allowed = this.candidatConnectedS.isLoggedIn();
        if (!allowed) {
            this.peConnectService.login(`${environment.urlAcces}${RoutesEnum.EVENTS}`);
        }
        return allowed;
    }
}
