import { Component, OnInit } from '@angular/core';
import { AccountService, AccountDTO, AccountType, AccountStatus } from '../../services/account.service';
import { UserService } from '../../services/user.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-account-list',
  templateUrl: './account-list.html',
  styleUrls: ['./account-list.scss'],
  imports: [CommonModule]
})
export class AccountListComponent implements OnInit {
  accounts: AccountDTO[] = [];
  loading = true;
  error: string | null = null;
  isAdmin = false;

  constructor(
    private accountService: AccountService,
    private userService: UserService
  ) {}

  ngOnInit(): void {
    // Determine if admin
    this.userService.getCurrentUser().subscribe({
      next: user => {
        this.isAdmin = user.role === 'ADMIN';
        this.fetchAccounts();
      },
      error: () => {
        this.error = 'Could not fetch user info';
        this.loading = false;
      }
    });
  }

  fetchAccounts() {
    this.loading = true;
    const obs = this.isAdmin
      ? this.accountService.getAllAccounts()
      : this.accountService.getMyAccounts();

    obs.subscribe({
      next: accounts => {
        this.accounts = accounts;
        this.loading = false;
      },
      error: err => {
        this.error = 'Failed to load accounts';
        this.loading = false;
      }
    });
  }
}