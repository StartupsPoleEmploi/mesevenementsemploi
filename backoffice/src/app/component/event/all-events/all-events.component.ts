import {Component, OnInit} from '@angular/core';
import {IEvent} from '../../../model/IEvent';
import {EventApiService} from '../../../shared/services/event-api.service';
import IAgency from '../../../model/IAgency';
import {TokenStorageService} from '../../../shared/services/token-storage.service';
import {AgencyService} from '../../../shared/services/agency.service';
import {ExportExcelService} from '../../../shared/services/export-excel.service';
import {environment} from '../../../../environments/environment';
import {NavbarService} from '../../../shared/services/navbar.service';
import {NavigationService} from '../../../shared/services/navigation.service';

@Component({
  selector: 'app-all-events',
  templateUrl: './all-events.component.html',
  styleUrls: ['./all-events.component.scss']
})
export class AllEventsComponent implements OnInit {

  public events: IEvent[] = [];
  public id = 0;
  public IsWait = false;
  public idAgence: string | null = '';
  public libelle = '';
  public agence: IAgency | undefined;
  public headers = ['Nom événement', 'Type', 'Date', 'Heure', 'Inscrits/Places totales', 'Statut', 'Actions'];
  public DirectionCode = ['00001', '31096', '82013', '81003', '66004', '65020', '34300', '31403', '30600', '11030'];
  public totalItems: number | undefined;
  public page = 1;
  public count = 0;
  public pageNb: any;
  public pageRequired: any;

  constructor(private eventService: EventApiService,
              private exportExcelService: ExportExcelService,
              private tokenStorageService: TokenStorageService,
              private agencyService: AgencyService,
              private navbarService: NavbarService,
              private navigationService: NavigationService) {
    this.events = [];
    this.navbarService.show();
    this.getCountEventNumber().subscribe((res: any) => {
      this.totalItems = res?.count;
      this.navbarService.handleTabs(
        false,
        `${this.totalItems} événements`,
        true,
        false,
        false,
        true,
        false,
        false,
        false,
        false,
      );
    });
  }

  ngOnInit(): void {
    this.idAgence = this.tokenStorageService.getUser();
    if (this.idAgence != null) {
      this.agencyService.getAgencyByCode(this.idAgence).subscribe(data => {
        this.agence = data;
        if (this.agence !== undefined) {
          this.libelle = this.agence.libelle;
        }
        return;
      });
    }
    this.navigationService.numberFromMap.subscribe(pageFromMap => {
      this.page = pageFromMap;
    });
    this.getAllEventsByAgency(this.idAgence, this.page - 1);
  }

  public getAllEventsByAgency(codeAgence: string | null, page: number): any {
    if (this.DirectionCode.includes(codeAgence as string)) {
      this.eventService.getAllEventsSorted(page).subscribe(res => {
        this.events = res;
      });
      this.eventService.getTotalEventNumber().subscribe((res: any) => {
        this.totalItems = res?.count;
      });
    } else {
      this.eventService.getAllEventsSortedByAgency(codeAgence, page).subscribe(res => {
        this.events = res;
      });
      this.eventService.getTotalEventByAgency(codeAgence).subscribe((res: any) => {
        this.totalItems = res?.count;
      });
    }
  }

  public getCountEventNumber(): any {
    this.idAgence = this.tokenStorageService.getUser();
    if (this.DirectionCode.includes(this.idAgence as string)) {
      return this.eventService.getTotalEventNumber();
    }
    else {
      return this.eventService.getTotalEventByAgency(this.idAgence);
    }
  }

  public onTableDataChange(page: any): any {
    this.page = page;
    this.getAllEventsByAgency(this.idAgence, this.page - 1);
    this.navigationService.updatePageNumber(this.page);
  }

  public deleteEvent(event: IEvent): void {
    const index = this.events.indexOf(event);
    if (index > -1) {
      this.events.splice(index, 1);
    }
  }

  public exportEvents(): void {
    if (!this.DirectionCode.includes(this.idAgence as string)) {
      this.downloadAllEvents(environment.peactonsDomainePath + '/export/evenements/agence/' + this.idAgence);
    } else {
      this.downloadAllEvents(environment.peactonsDomainePath +  '/export/evenements/all');
    }
  }

  public downloadAllEvents(url: string): void {
    this.IsWait = true;
    fetch(url)
      .then(response => response.blob())
      .then(blob => URL.createObjectURL(blob))
      .then(uril => {
        const link = document.createElement('a');
        link.href = uril;
        link.download = 'evenements.xlsx';
        document.body.appendChild(link);
        link.click();
        document.body.removeChild(link);
        this.IsWait = false;
      });
  }

  public onSearchPage(page: number, lastPage: number, currentPage: number): any {
    if (page != 0 && page <= lastPage) {
      this.page = page;
      this.getAllEventsByAgency(this.idAgence, this.page - 1);
      this.navigationService.updatePageNumber(this.page);
    } else {
      this.getAllEventsByAgency(this.idAgence, currentPage);
      this.navigationService.updatePageNumber(currentPage);
      this.pageNb = '';
    }
  }
}

