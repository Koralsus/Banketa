import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AccountAdminSearch } from './account-admin-search';

describe('AccountAdminSearch', () => {
  let component: AccountAdminSearch;
  let fixture: ComponentFixture<AccountAdminSearch>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AccountAdminSearch]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AccountAdminSearch);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
