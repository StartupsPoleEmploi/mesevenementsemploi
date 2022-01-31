import {ComponentFixture, TestBed} from '@angular/core/testing';

import {InscriptionKoComponent} from './inscription-ko.component';

describe('InscriptionKoComponent', () => {
  let component: InscriptionKoComponent;
  let fixture: ComponentFixture<InscriptionKoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [InscriptionKoComponent]
    })
        .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(InscriptionKoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
