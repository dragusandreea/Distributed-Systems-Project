import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UsersQueryPageComponent } from './users-query-page.component';

describe('UsersQueryPageComponent', () => {
  let component: UsersQueryPageComponent;
  let fixture: ComponentFixture<UsersQueryPageComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UsersQueryPageComponent]
    });
    fixture = TestBed.createComponent(UsersQueryPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
