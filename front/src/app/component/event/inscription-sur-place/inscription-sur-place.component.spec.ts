import {ComponentFixture, TestBed} from '@angular/core/testing';

import {InscriptionSurPlaceComponent} from './inscription-sur-place.component';

describe('InscriptionSurPlaceComponent', () => {
  let component: InscriptionSurPlaceComponent;
  let fixture: ComponentFixture<InscriptionSurPlaceComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [InscriptionSurPlaceComponent]
    })
        .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(InscriptionSurPlaceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
