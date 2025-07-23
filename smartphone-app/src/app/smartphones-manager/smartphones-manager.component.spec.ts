import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SmartphonesManagerComponent } from './smartphones-manager.component';

describe('SmartphonesManagerComponent', () => {
  let component: SmartphonesManagerComponent;
  let fixture: ComponentFixture<SmartphonesManagerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SmartphonesManagerComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SmartphonesManagerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
