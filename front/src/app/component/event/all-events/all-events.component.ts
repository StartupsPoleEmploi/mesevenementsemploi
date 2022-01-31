import {Component, OnInit, ViewChild} from '@angular/core';
import {FormControl} from '@angular/forms';
import {MatTableDataSource} from '@angular/material/table';
import {MatSelectionList} from '@angular/material/list';
import {BreakpointObserver} from '@angular/cdk/layout';
import {MatSelect} from '@angular/material/select';
import {Router} from '@angular/router';

import {map} from 'rxjs/operators';
import {Observable} from 'rxjs';

import {ModaliteAccesService} from '../../../shared/services/modalite-acces.service';
import {DepartementService} from '../../../shared/services/departement.service';
import {PrerequisService} from '../../../shared/services/prerequis.service';
import {EventApiService} from '../../../shared/services/event-api.service';
import {TagService} from '../../../shared/services/tag.service';

import {IEvent} from '../../../model/IEvent';
import {IDepartement} from '../../../model/IDepartement';
import {UrlService} from '../../../shared/services/url.service';
import ITag from '../../../model/ITag';
import { NavigationService } from 'src/app/shared/services/navigation.service';

@Component({
    selector: 'app-all-events',
    templateUrl: './all-events.component.html',
    styleUrls: ['./all-events.component.scss'],
})
export class AllEventsComponent implements OnInit {

    @ViewChild(MatSelectionList, {static: true})
    public matSelectionOptionsComponentRef: MatSelect | undefined;
    @ViewChild(MatSelectionList, {static: true})
    matSelectionOptionsListComponentRef: MatSelectionList | undefined;

    private count = 0;
    private DISTANCE_TXT = 'A distance';
    private DIPLOME_ID = 0;
    private PUBLIC_CIBLE_ID = 0;
    private TYPE_EVENEMENT_ID = 2;
    private dipolomeList: any;
    private typeList: any;
    private publicList: any;
    private particiaptionList: any;
    private dataSource: any;
    private publicTargetId = 0;
    private previousUrl: Observable<string> = this.urlService.previousUrl$;
    private originUrl = '';

    public events: IEvent[] = [];
    public lieu: FormControl = new FormControl();
    public totalItems: number | undefined;
    public currentPage = 0;
    public itemsPerPage = 10;
    public departementList = new Array<IDepartement>();
    public isSmallScreen: Observable<boolean>;
    public isWideScreen: Observable<boolean>;
    public selectedDepartement: any = null;
    public eventLocation: string | undefined;
    public filteredDepartment = null;

    constructor(
        private eventService: EventApiService,
        private tagService: TagService,
        private modaliteAccessService: ModaliteAccesService,
        private departementService: DepartementService,
        private prerequisService: PrerequisService,
        private breakpointObserver: BreakpointObserver,
        private router: Router,
        private urlService: UrlService,
        private navigationService: NavigationService
        ) {
        this.isWideScreen = this.breakpointObserver
            .observe(['(min-width: 821px)'])
            .pipe(map(({matches}) => matches));
        this.isSmallScreen = this.breakpointObserver
            .observe(['(max-width: 820px)'])
            .pipe(map(({matches}) => matches));
    }

    ngOnInit(): void {
        this.previousUrl.subscribe(url => {
            this.originUrl = url;
        });
        this.getDepartement();
        this.getDiplomes();
        this.getPublicCibles();
        this.getParticipation();
        this.getTypesEvenement();
        this.dataSource = new MatTableDataSource(this.events);

        this.navigationService.numberFromMap.subscribe(pageFromMap => {
            this.currentPage = pageFromMap.page;
            this.selectedDepartement = pageFromMap.codeDepartement;
            if (this.selectedDepartement) {
                this.lieu.setValue(this.selectedDepartement);
            }
        });
        this.getEvents(this.currentPage - 1, this.selectedDepartement);
    }

    public onPageChange(page: number): any {
        this.currentPage = page;
        this.getEvents(page - 1, this.selectedDepartement);
        this.navigationService.updateFilterPageNumber(this.currentPage, this.selectedDepartement);
        // return page;
    }

    public getEventsBySelectedDept(selectedItem: any): void {
        if (this.filteredDepartment === selectedItem) { return; }
        this.filteredDepartment = selectedItem;
        this.currentPage = 0;
        this.selectedDepartement = selectedItem;
        this.navigationService.updateFilterPageNumber(this.currentPage, this.selectedDepartement);
        this.getEvents(this.currentPage, this.selectedDepartement);

    }

    public getOnSitePlaces(event: IEvent): number {
        const onSitePlace = event.modeAcceesList.filter(modality => {
            return modality.modaliteAcces.id === 1;
        });
        return onSitePlace[0] ? onSitePlace[0].nombrePlace - onSitePlace[0].nombreInscrit : 0;
    }

    public getRemotePlaces(event: IEvent): number {
        const remotePlace = event.modeAcceesList.filter(modality => {
            return modality.modaliteAcces.id === 0;
        });
        return remotePlace[0] ? remotePlace[0].nombrePlace - remotePlace[0].nombreInscrit : 0;
    }

    public reset(): void {
        this.currentPage = 0;
        this.selectedDepartement = null;
        this.navigationService.updateFilterPageNumber(this.currentPage, this.selectedDepartement);
        this.getEvents(0, this.selectedDepartement );
    }

    public isEventFull(event: IEvent): boolean {
        return this.getOnSitePlaces(event) === 0 && this.getRemotePlaces(event) === 0;
    }

    public getPublicTargetByEvent(event: IEvent): ITag[] {
        return event.tags.filter(tag => {
            return tag.typeTagId === this.publicTargetId;
        });
    }

    public handleClickedCard(eventId: number): void {
        this.isSmallScreen.subscribe(res => {
            if (res) {
                this.router.navigate(['/evenement/', eventId]);
            }
        });
    }

    private getDepartement(): void {
        const http$ = this.departementService.getAllDepartements();
        http$.subscribe(
            res => {
                this.departementList = res;
                this.departementList.splice(0, 0, {id: -1, code: '-1', libelle: this.DISTANCE_TXT});
            });
    }

    private getDiplomes(): void {
        const http$ = this.prerequisService.getAllPrerequisitesById(this.DIPLOME_ID);
        http$.subscribe(datas => {
                this.dipolomeList = datas;
            });
    }

    private getPublicCibles(): void {
        const http$ = this.tagService.getAllTagsByType(this.PUBLIC_CIBLE_ID);
        http$.subscribe(datas => {
                this.publicList = datas;
            });
    }

    private getTypesEvenement(): void {
        const http$ = this.tagService.getAllTagsByType(this.TYPE_EVENEMENT_ID);
        http$.subscribe(datas => {
                this.typeList = datas;
            });
    }

    private getParticipation(): void {
        const http$ = this.modaliteAccessService.getAllModaliteAccess();
        http$.subscribe(
            res => {
                this.particiaptionList = res;
            });
    }

    private getEvents(pageNo: number, codeDept: any): void {
        const http$ = this.eventService.getAllEvents(pageNo, codeDept ? codeDept : 'null');
        const httpCount = this.eventService.getTotalEventNumber(codeDept ? codeDept : 'null');

        httpCount.subscribe(
            (res: any) => {
                this.totalItems = res.count;
            });

        http$.subscribe(
            (res: IEvent[]) => {
                this.events = res.filter(event => event.statut !== 'Terminé');
            });
    }

    public getEventLocation(event: IEvent): string | undefined {
        if (event?.codePostal && event?.ville) {
            this.eventLocation = event.codePostal + ' ' + event?.ville;
        } else if (
            (!event?.codePostal && !event?.ville) &&
            (this.getOnSitePlaces(event) !== undefined && this.getRemotePlaces(event) > 0)
        ) {
            this.eventLocation = undefined;
        } else {
            this.eventLocation = 'A distance';
        }
        return this.eventLocation;
    }

}
