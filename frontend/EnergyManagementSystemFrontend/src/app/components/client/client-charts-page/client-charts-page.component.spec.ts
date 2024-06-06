import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ClientChartsPageComponent } from './client-charts-page.component';

describe('ClientChartsPageComponent', () => {
  let component: ClientChartsPageComponent;
  let fixture: ComponentFixture<ClientChartsPageComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ClientChartsPageComponent]
    });
    fixture = TestBed.createComponent(ClientChartsPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
