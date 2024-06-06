import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UsersManagePageComponent } from './users-manage-page.component';

describe('UsersManagePageComponent', () => {
  let component: UsersManagePageComponent;
  let fixture: ComponentFixture<UsersManagePageComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UsersManagePageComponent]
    });
    fixture = TestBed.createComponent(UsersManagePageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
