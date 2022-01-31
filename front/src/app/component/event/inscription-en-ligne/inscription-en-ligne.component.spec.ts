import {ComponentFixture, TestBed} from '@angular/core/testing';

import {InscriptionEnLigneComponent} from './inscription-en-ligne.component';

describe('InscriptionEnLigneComponent', () => {
  let component: InscriptionEnLigneComponent;
  let fixture: ComponentFixture<InscriptionEnLigneComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [InscriptionEnLigneComponent]
    })
        .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(InscriptionEnLigneComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
