import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {AuthenticationComponent} from './component/authentication/authentication.component';
import {AuthGuardService} from './shared/services/auth-guard.service';
import {AllEventsComponent} from './component/event/all-events/all-events.component';
import {CreateEventComponent} from './component/event/create-event/create-event.component';
import {CandidatesListComponent} from './component/event/candidat/candidates-list.component';
import {AddRegisteredComponent} from './component/event/candidat/add-registered/add-registered.component';

const routes: Routes = [
  {
    path: 'connexion',
    component: AuthenticationComponent
  },
  {
    path: 'evenements',
    component: AllEventsComponent,
    canActivate: [AuthGuardService]
  },
  {
    path: 'creer/evenement',
    component: CreateEventComponent,
    canActivate: [AuthGuardService]
  },
  {
    path: 'editer/evenement/:eventId',
    component: CreateEventComponent,
    canActivate: [AuthGuardService]
  },
  {
    path: 'candidats/:eventId',
    component: CandidatesListComponent,
    canActivate: [AuthGuardService]
  },
  {
    path: 'addInscrit/:eventId',
    component: AddRegisteredComponent,
    canActivate: [AuthGuardService]
  },
  {
    path: '**',
    redirectTo: 'evenements'
  }];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
