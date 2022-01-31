import {ComponentFixture, TestBed} from '@angular/core/testing';

import {CandidatDejaInscritComponent} from './candidat-deja-inscrit.component';

describe('CandidatDejaInscritComponent', () => {
  let component: CandidatDejaInscritComponent;
  let fixture: ComponentFixture<CandidatDejaInscritComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CandidatDejaInscritComponent]
    })
        .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CandidatDejaInscritComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
