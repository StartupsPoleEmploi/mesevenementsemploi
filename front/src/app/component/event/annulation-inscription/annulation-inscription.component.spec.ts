import {ComponentFixture, TestBed} from '@angular/core/testing';

import {AnnulationInscriptionComponent} from './annulation-inscription.component';

describe('AnnulationInscriptionComponent', () => {
  let component: AnnulationInscriptionComponent;
  let fixture: ComponentFixture<AnnulationInscriptionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AnnulationInscriptionComponent]
    })
        .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AnnulationInscriptionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
