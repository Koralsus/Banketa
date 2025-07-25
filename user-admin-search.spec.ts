import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserAdminSearch } from './user-admin-search';

describe('UserAdminSearch', () => {
  let component: UserAdminSearch;
  let fixture: ComponentFixture<UserAdminSearch>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UserAdminSearch]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UserAdminSearch);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
