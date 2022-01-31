import {ComponentFixture, TestBed} from '@angular/core/testing';

import {PlacesNonDisponiblesComponent} from './places-non-disponibles.component';

describe('PlacesNonDisponiblesComponent', () => {
  let component: PlacesNonDisponiblesComponent;
  let fixture: ComponentFixture<PlacesNonDisponiblesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [PlacesNonDisponiblesComponent]
    })
        .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PlacesNonDisponiblesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
