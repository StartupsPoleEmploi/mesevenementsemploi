import {ComponentFixture, TestBed} from '@angular/core/testing';

import {EventTermineComponent} from './event-termine.component';

describe('EventTermineComponent', () => {
  let component: EventTermineComponent;
  let fixture: ComponentFixture<EventTermineComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [EventTermineComponent]
    })
        .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EventTermineComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
