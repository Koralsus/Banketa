import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TransactionAdminSearch } from './transaction-admin-search';

describe('TransactionAdminSearch', () => {
  let component: TransactionAdminSearch;
  let fixture: ComponentFixture<TransactionAdminSearch>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TransactionAdminSearch]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TransactionAdminSearch);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
