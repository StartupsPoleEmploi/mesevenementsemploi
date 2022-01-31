import {Component, EventEmitter, Output} from '@angular/core';
import {BreakpointObserver} from '@angular/cdk/layout';
import {Observable} from 'rxjs';
import {map} from 'rxjs/operators';
import {NavbarService} from '../../services/navbar.service';
import {Router} from '@angular/router';
import {Location} from '@angular/common';

@Component({
  selector: 'app-toolbar',
  templateUrl: './toolbar.component.html',
  styleUrls: ['./toolbar.component.scss']
})
export class ToolbarComponent {

  @Output() public sidenavToggle = new EventEmitter();
  @Output() public newStateIdEvent = new EventEmitter<number>();
  @Output() public exportEvent = new EventEmitter<boolean>();
  @Output() public backClickedEvent = new EventEmitter<any>();
  @Output() public saveCandidatEvent = new EventEmitter<any>();
  @Output() public exportCandidatesEvent = new EventEmitter<boolean>();
  @Output() public navigateToAddNewRegisteredEvent = new EventEmitter<any>();
  public createBtnLabel = 'CRÉER UN EVENEMENT';
  public exportBtnLabel = 'EXPORTER LES EVENEMENTS';
  public saveBtnLabel = 'ENREGISTRER EN BROUILLON';
  public publishBtnLabel = 'PUBLIER';
  public recordBtnLabel = 'INSCRIRE UN CANDIDAT';
  public exportRegisteredBtnLabel = 'EXPORTER LES INSCRITS';
  public isSmallScreen: Observable<boolean>;
  public isWideScreen: Observable<boolean>;

  constructor(
    public navbarService: NavbarService,
    private location: Location,
    private breakpointObserver: BreakpointObserver,
    private router: Router) {
    this.isSmallScreen = this.breakpointObserver
      .observe(['(min-width: 821px)'])
      .pipe(map(({matches}) => matches));
    this.isWideScreen = this.breakpointObserver
      .observe(['(max-width: 820px)'])
      .pipe(map(({matches}) => matches));
  }

  public onToggleSidenav(): void {
    this.sidenavToggle.emit();
  }

  public toRegister(): void {
    this.newStateIdEvent.emit(0);
  }

  public toPublish(): void {
    this.newStateIdEvent.emit(1);
  }

  public return(): void {
    this.router.navigate(['evenements']);
  }

  public exportEvents(): void {
    this.exportEvent.emit();
  }

  public backClicked(): void {
    this.backClickedEvent.emit(this.location.back());
  }

  public saveCandidat(): void {
    this.saveCandidatEvent.emit();
  }

  public navigateToAddNewRegistered(): void {
    this.navigateToAddNewRegisteredEvent.emit();
  }

  public exportCandidates(): void {
    this.exportCandidatesEvent.emit();
  }

}
