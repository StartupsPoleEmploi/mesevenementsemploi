import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {DatePipe, registerLocaleData} from '@angular/common';
import {HttpClientModule} from '@angular/common/http';


import {MatSliderModule} from '@angular/material/slider';
import {MatOptionModule} from '@angular/material/core';
import {MatSelectModule} from '@angular/material/select';
import {MatButtonModule} from '@angular/material/button';
import {MatListModule} from '@angular/material/list';
import {MatGridListModule} from '@angular/material/grid-list';
import {MatCheckboxModule} from '@angular/material/checkbox';
import {MatDialogModule} from '@angular/material/dialog';
import {MatSnackBarModule} from '@angular/material/snack-bar';
import {MatIconModule} from '@angular/material/icon';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatToolbarModule} from '@angular/material/toolbar';

import {AppRoutingModule} from './app-routing.module';
import {NgxWebstorageModule} from 'ngx-webstorage';
import {NgxPaginationModule} from 'ngx-pagination';
import {CookieService} from 'ngx-cookie-service';
import {Ng2SearchPipeModule} from 'ng2-search-filter';
import {TransferHttpCacheModule} from '@nguniversal/common';

import {AppComponent} from './app.component';
import {HeaderComponent} from './shared/component/header/header.component';
import {FooterComponent} from './shared/component/footer/footer.component';
import {AllEventsComponent} from './component/event/all-events/all-events.component';
import {DetailsEventComponent} from './component/event/details-event/details-event.component';
import {PrerequisitesComponent} from './component/event/prerequisites/prerequisites.component';
import {InscriptionKoComponent} from './component/event/inscription-ko/inscription-ko.component';
import {InscriptionEnLigneComponent} from './component/event/inscription-en-ligne/inscription-en-ligne.component';
import {PlacesNonDisponiblesComponent} from './component/event/places-non-disponibles/places-non-disponibles.component';
import {CandidatDejaInscritComponent} from './component/event/candidat-deja-inscrit/candidat-deja-inscrit.component';
import {EventTermineComponent} from './component/event/event-termine/event-termine.component';
import {InscriptionComponent} from './component/event/inscription/inscription.component';
import {ErrorAuthenticationComponent} from './component/event/error-authentication/error-authentication.component';
import {InscriptionSurPlaceComponent} from './component/event/inscription-sur-place/inscription-sur-place.component';


import {CguComponent} from './shared/component/cgu/cgu.component';
import {DialogConfirmationComponent} from './shared/component/dialog-confirmation/dialog-confirmation.component';
import frenchLocale from '@angular/common/locales/fr';
import {PeConnectService} from './shared/services/pe-connect.service';
import {ModalSessionExpiredComponent} from './shared/component/modal-session-expired/modal-session-expired.component';
import {AuthGuard} from './shared/guards/auth-guard';
import {UrlService} from './shared/services/url.service';
import {NavigationService} from './shared/services/navigation.service';
import {AccessibiliteComponent} from './shared/component/accessibilite/accessibilite.component';
import {AnnulationInscriptionComponent} from './component/event/annulation-inscription/annulation-inscription.component';
import {DialogAnnulationComponent} from './shared/component/dialog-annulation/dialog-annulation.component';

registerLocaleData(frenchLocale);

@NgModule({
    declarations: [
        AppComponent,
        CguComponent,
      HeaderComponent,
      FooterComponent,
      AllEventsComponent,
      DetailsEventComponent,
      PrerequisitesComponent,
        DialogConfirmationComponent,
        InscriptionComponent,
        InscriptionKoComponent,
        InscriptionSurPlaceComponent,
        InscriptionEnLigneComponent,
        PlacesNonDisponiblesComponent,
        ModalSessionExpiredComponent,
        CandidatDejaInscritComponent,
        EventTermineComponent,
        ErrorAuthenticationComponent,
        AccessibiliteComponent,
        AnnulationInscriptionComponent,
        DialogAnnulationComponent
    ],
    imports: [
        BrowserModule.withServerTransition({appId: 'mee-candidat'}),
        TransferHttpCacheModule,
        Ng2SearchPipeModule,
        HttpClientModule,
        BrowserAnimationsModule,
        NgxWebstorageModule.forRoot(),
        MatSliderModule,
        MatOptionModule,
        MatSelectModule,
        MatListModule,
        MatButtonModule,
        MatGridListModule,
        ReactiveFormsModule,
        NgxPaginationModule,
        MatIconModule,
        MatCheckboxModule,
        MatDialogModule,
        MatFormFieldModule,
        MatSnackBarModule,
        MatToolbarModule,
        AppRoutingModule,
        FormsModule
    ],
  providers: [
    CookieService, PeConnectService, DatePipe, AuthGuard, UrlService, NavigationService
  ],
  bootstrap: [AppComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppModule { }
