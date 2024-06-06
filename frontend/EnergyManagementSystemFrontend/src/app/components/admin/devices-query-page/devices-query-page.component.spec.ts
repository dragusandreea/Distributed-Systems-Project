import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DevicesQueryPageComponent } from './devices-query-page.component';

describe('DevicesQueryPageComponent', () => {
  let component: DevicesQueryPageComponent;
  let fixture: ComponentFixture<DevicesQueryPageComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DevicesQueryPageComponent]
    });
    fixture = TestBed.createComponent(DevicesQueryPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
