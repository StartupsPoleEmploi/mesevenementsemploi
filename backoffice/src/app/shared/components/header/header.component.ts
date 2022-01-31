import {Component, OnInit} from '@angular/core';
import {TokenStorageService} from '../../services/token-storage.service';
import IAgency from '../../../model/IAgency';
import {AgencyService} from '../../services/agency.service';
import {Router} from '@angular/router';
import {Location} from '@angular/common';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {
  public identifiant: string | null = '';
  public libelle = '';
  public agence: IAgency | undefined;

  constructor(private tokenStorageService: TokenStorageService,
              private agencyService: AgencyService,
              private router: Router,
              private location: Location) {
  }

  ngOnInit(): void {
    this.identifiant = this.tokenStorageService.getUser();
    if (this.identifiant != null) {
      this.agencyService.getAgencyByCode(this.identifiant).subscribe(data => {
        this.agence = data;
        if (this.agence !== undefined) {
          this.libelle = this.agence.libelle;
        }
        return;
      });
    }
  }

  public backClicked(): void {
    this.location.back();
  }

  signOut(): void {
    this.tokenStorageService.signOut();
  }
}
