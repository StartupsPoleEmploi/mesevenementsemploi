import {ComponentFixture, TestBed} from '@angular/core/testing';

import {AddRegisteredComponent} from './add-registered.component';

describe('AddRegisteredComponent', () => {
  let component: AddRegisteredComponent;
  let fixture: ComponentFixture<AddRegisteredComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AddRegisteredComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AddRegisteredComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
