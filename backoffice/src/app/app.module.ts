import {CUSTOM_ELEMENTS_SCHEMA, LOCALE_ID, NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatChipsModule} from '@angular/material/chips';
import {DatePipe, registerLocaleData} from '@angular/common';
import {NgxMaterialTimepickerModule} from 'ngx-material-timepicker';
import {DateAdapter} from '@angular/material/core';

import {AppRoutingModule} from './app-routing.module';
import {NgxPaginationModule} from 'ngx-pagination';
import {SharedModule} from './shared/shared.module';
import {AuthInterceptor} from './shared/Interceptor/auth.interceptor';
import {NavbarService} from './shared/services/navbar.service';

import {AppComponent} from './app.component';
import {AuthenticationComponent} from './component/authentication/authentication.component';
import {EventComponent} from './component/event/event.component';
import {CreateEventComponent} from './component/event/create-event/create-event.component';
import {AllEventsComponent} from './component/event/all-events/all-events.component';
import {DialogConfirmationComponent} from './shared/components/dialog-confirmation/dialog-confirmation.component';
import {RecruitersComponent} from './component/event/create-event/recruiters/recruiters.component';
import {PrerequisitesComponent} from './component/event/create-event/prerequisites/prerequisites.component';
import {DetailsEventComponent} from './component/event/details-event/details-event.component';
import {CandidatesListComponent} from './component/event/candidat/candidates-list.component';
import {TagsComponent} from './component/event/create-event/tags/tags.component';
import {DetailsCandidatComponent} from './component/event/candidat/details-candidat/details-candidat.component';
import {NafComponent} from './component/event/create-event/naf/naf.component';
import {RomeComponent} from './component/event/create-event/rome/rome.component';
import {ToolbarComponent} from './shared/components/toolbar/toolbar.component';
import {OfferComponent} from './component/event/create-event/offer/offer.component';
import {RomeService} from './shared/services/rome.service';
import {CustomDateAdapter} from './shared/custom-date-adapter';
import localeFr from '@angular/common/locales/fr';
import {AddRegisteredComponent} from './component/event/candidat/add-registered/add-registered.component';
import {NavigationService} from './shared/services/navigation.service';

registerLocaleData(localeFr, 'fr');

@NgModule({
  declarations: [
    AppComponent,
    AuthenticationComponent,
    EventComponent,
    DialogConfirmationComponent,
    CreateEventComponent,
    AllEventsComponent,
    RecruitersComponent,
    PrerequisitesComponent,
    DetailsEventComponent,
    CandidatesListComponent,
    DetailsCandidatComponent,
    NafComponent,
    RomeComponent,
    TagsComponent,
    DetailsCandidatComponent,
    ToolbarComponent,
    OfferComponent,
    AddRegisteredComponent,
  ],
  imports: [
    SharedModule,
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    BrowserAnimationsModule,
    ReactiveFormsModule,
    MatChipsModule,
    NgxPaginationModule,
    NgxMaterialTimepickerModule
  ],
  providers: [AuthInterceptor, DatePipe, NavbarService, NavigationService, RomeService,
    {provide: DateAdapter, useClass: CustomDateAdapter},
    {provide: LOCALE_ID, useValue: 'fr'}],
  bootstrap: [AppComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppModule {
  constructor(private dateAdapter: DateAdapter<Date>) {
    this.dateAdapter.setLocale('fr-FR');
  }
}
