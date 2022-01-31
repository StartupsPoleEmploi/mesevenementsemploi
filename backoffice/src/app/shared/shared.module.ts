import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {MatSnackBarModule} from '@angular/material/snack-bar';
import {MatSelectModule} from '@angular/material/select';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import {MatDatepickerModule} from '@angular/material/datepicker';
import {MAT_DATE_LOCALE, MatNativeDateModule} from '@angular/material/core';
import {MatListModule} from '@angular/material/list';
import {MatTableModule} from '@angular/material/table';
import {MatIconModule} from '@angular/material/icon';
import {MatButtonModule} from '@angular/material/button';
import {MatGridListModule} from '@angular/material/grid-list';
import {MatCardModule} from '@angular/material/card';
import {HammerModule} from '@angular/platform-browser';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatExpansionModule} from '@angular/material/expansion';
import {MatDialogModule} from '@angular/material/dialog';
import {MatPaginatorModule} from '@angular/material/paginator';
import {MatSortModule} from '@angular/material/sort';
import {MatCheckboxModule} from '@angular/material/checkbox';
import {MatMenuModule} from '@angular/material/menu';
import {MatRadioModule} from '@angular/material/radio';
import {MatSlideToggleModule} from '@angular/material/slide-toggle';
import {HeaderComponent} from './components/header/header.component';
import {FooterComponent} from './components/footer/footer.component';
import {RouterModule} from '@angular/router';
import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';
import {MatToolbarModule} from '@angular/material/toolbar';
import {BackButtonDirective} from './back-button.directive';

const MATERIAL_MODULES = [
  MatSnackBarModule,
  MatSelectModule,
  MatFormFieldModule,
  MatInputModule,
  MatDatepickerModule,
  MatProgressSpinnerModule,
  MatNativeDateModule,
  MatMenuModule,
  HammerModule,
  MatDialogModule,
  MatSnackBarModule,
  MatExpansionModule,
  BrowserAnimationsModule,
  MatListModule,
  MatSlideToggleModule,
  MatTableModule,
  MatIconModule,
  MatCardModule,
  MatGridListModule,
  MatButtonModule,
  MatPaginatorModule,
  MatSortModule,
  MatCheckboxModule,
  MatRadioModule,
  MatToolbarModule,
];


const MODULES = [CommonModule, ...MATERIAL_MODULES];

@NgModule({
  declarations: [HeaderComponent, FooterComponent, BackButtonDirective],
  imports: [...MODULES, RouterModule],
  exports: [...MODULES, HeaderComponent, BackButtonDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  providers: [{provide: MAT_DATE_LOCALE, useValue: 'fr-FR'}]
})
export class SharedModule {}
