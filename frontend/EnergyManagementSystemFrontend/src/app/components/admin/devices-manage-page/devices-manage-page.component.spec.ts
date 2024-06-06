import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DevicesManagePageComponent } from './devices-manage-page.component';

describe('DevicesManagePageComponent', () => {
  let component: DevicesManagePageComponent;
  let fixture: ComponentFixture<DevicesManagePageComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DevicesManagePageComponent]
    });
    fixture = TestBed.createComponent(DevicesManagePageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
