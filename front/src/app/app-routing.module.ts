import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {AllEventsComponent} from './component/event/all-events/all-events.component';
import {CguComponent} from './shared/component/cgu/cgu.component';
import {DetailsEventComponent} from './component/event/details-event/details-event.component';
import {InscriptionKoComponent} from './component/event/inscription-ko/inscription-ko.component';
import {InscriptionEnLigneComponent} from './component/event/inscription-en-ligne/inscription-en-ligne.component';
import {PlacesNonDisponiblesComponent} from './component/event/places-non-disponibles/places-non-disponibles.component';
import {CandidatDejaInscritComponent} from './component/event/candidat-deja-inscrit/candidat-deja-inscrit.component';
import {EventTermineComponent} from './component/event/event-termine/event-termine.component';
import {ErrorAuthenticationComponent} from './component/event/error-authentication/error-authentication.component';
import {PrerequisitesComponent} from './component/event/prerequisites/prerequisites.component';
import {InscriptionSurPlaceComponent} from './component/event/inscription-sur-place/inscription-sur-place.component';
import {InscriptionComponent} from './component/event/inscription/inscription.component';
import {AuthGuard} from './shared/guards/auth-guard';
import {AccessibiliteComponent} from './shared/component/accessibilite/accessibilite.component';
import {AnnulationInscriptionComponent} from './component/event/annulation-inscription/annulation-inscription.component';

const appRoutes: Routes = [
  {
    path: 'evenements',
    component: AllEventsComponent,
  },
  {
    path: 'cgu',
    component: CguComponent,
  },
  {
    path: 'annulation/:eventId',
    component: AnnulationInscriptionComponent,
  },
  {
    path: 'accessibilite',
    component: AccessibiliteComponent,
  },
  {
    path: 'evenement/:eventId',
    component: DetailsEventComponent,
  },
  {
    path: 'evenement',
    component: DetailsEventComponent,
  },
  {
    path: 'prerequis',
    component: PrerequisitesComponent,
  },
  {
    path: 'confirmationSurPlace',
    component: InscriptionSurPlaceComponent,
    canActivate: [AuthGuard],
  },
  {
    path: 'inscription/:eventId',
    component: InscriptionComponent,
  },
  {
    path: 'placesNonDisponibles',
    component: PlacesNonDisponiblesComponent,
    canActivate: [AuthGuard],
  },
  {
    path: 'confirmationEnLigne',
    component: InscriptionEnLigneComponent,
    canActivate: [AuthGuard],
  },
  {
    path: 'inscriptionKo',
    component: InscriptionKoComponent,
    canActivate: [AuthGuard],
  },
  {
    path: 'DejaInscrit',
    component: CandidatDejaInscritComponent,
    canActivate: [AuthGuard],
  },
  {
    path: 'EventTermine',
    component: EventTermineComponent,
  },
  {
    path: 'ErrorAuthentication',
    component: ErrorAuthenticationComponent,
  },
  {
    path: '',
    redirectTo: 'evenements',
    pathMatch: 'full',
  },
  {
    path: '**',
    redirectTo: 'evenements',
  },
];

@NgModule({
  imports: [RouterModule.forRoot(
      appRoutes,
      { enableTracing: true }
  )],
  exports: [RouterModule]
})
export class AppRoutingModule { }
