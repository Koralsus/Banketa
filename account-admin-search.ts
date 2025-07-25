import { Component } from '@angular/core';
import { AccountService, AccountDTO } from '../../services/account.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AccountStatus, AccountType } from '../../models/account.enums';

@Component({
  selector: 'app-account-admin-search',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './account-admin-search.html',
  styleUrl: './account-admin-search.scss'
})
export class AccountAdminSearchComponent {
  accountNumberQuery = '';
  accountTypeQuery: AccountType | null = null;
  accountStatusQuery: AccountStatus | null = null;
  creationDateQuery: Date | null = null;
  accounts: AccountDTO[] = [];
  error = '';

  constructor(private accountService: AccountService) {}

  searchByAccountNumber() {
    if (!this.accountNumberQuery) return;
    this.accountService.searchByAccountNumber(this.accountNumberQuery)
      .subscribe({
        next: acc => { this.accounts = acc ? [acc] : []; this.error = ''; },
        error: err => { this.error = 'No account found.'; this.accounts = []; }
      });
  }

  searchByAccountType() {
    if (!this.accountTypeQuery) return;
    this.accountService.searchByAccountType(this.accountTypeQuery)
      .subscribe({
        next: accs => { this.accounts = accs; this.error = ''; },
        error: err => { this.error = 'No accounts found.'; this.accounts = []; }
      });
  }

  searchByAccountStatus() {
    if (!this.accountStatusQuery) return;
    this.accountService.searchByAccountStatus(this.accountStatusQuery)
      .subscribe({
        next: accs => { this.accounts = accs; this.error = ''; },
        error: err => { this.error = 'No accounts found.'; this.accounts = []; }
      });
  }

  searchByCreationDate() {
    if (!this.creationDateQuery) return;
    const dateStr = this.creationDateQuery.toISOString().slice(0, 10);
    this.accountService.searchByCreationDate(dateStr)
      .subscribe({
        next: accs => { this.accounts = accs; this.error = ''; },
        error: err => { this.error = 'No accounts found.'; this.accounts = []; }
      });
  }
}