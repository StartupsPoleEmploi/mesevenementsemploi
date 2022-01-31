import {ComponentFixture, TestBed} from '@angular/core/testing';

import {DialogAnnulationComponent} from './dialog-annulation.component';

describe('DialogAnnulationComponent', () => {
  let component: DialogAnnulationComponent;
  let fixture: ComponentFixture<DialogAnnulationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [DialogAnnulationComponent]
    })
        .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DialogAnnulationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
