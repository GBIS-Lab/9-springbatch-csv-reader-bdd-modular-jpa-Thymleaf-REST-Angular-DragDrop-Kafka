import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SmartphonesPageComponent } from './smartphones-page.component';

describe('SmartphonesPageComponent', () => {
  let component: SmartphonesPageComponent;
  let fixture: ComponentFixture<SmartphonesPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SmartphonesPageComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SmartphonesPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
