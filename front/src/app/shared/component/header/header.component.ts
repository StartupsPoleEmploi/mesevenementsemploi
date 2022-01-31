import {Component, Input, OnInit} from '@angular/core';
import {PeConnectService} from '../../services/pe-connect.service';
import {CandidatConnectedService} from '../../services/candidat-connected.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html'
})
export class HeaderComponent implements OnInit {
  @Input() minified: boolean = false;

  constructor(private peConnectService: PeConnectService,
              public candidatConnected: CandidatConnectedService,
              private router: Router) {
  }

  ngOnInit(): void {}

  signOut(): void {
    this.peConnectService.logout();
  }

  public backHome(): void {
    this.router.navigate(['evenements']);
  }
}
