import { Component, OnInit } from '@angular/core';
import { CommonModule, DecimalPipe } from '@angular/common';
import { AccountListComponent } from '../account-list/account-list';
import { AccountService, AccountDTO } from '../../services/account.service';
import { TransactionService, TransactionDTO } from '../../services/transaction.service';
import { UserAdminSearchComponent } from "../user-admin-search/user-admin-search";

@Component({
  selector: 'app-admin-dashboard',
  standalone: true,
  imports: [CommonModule, DecimalPipe, AccountListComponent, UserAdminSearchComponent],
  templateUrl: './admin-dashboard.html',
  styleUrl: './admin-dashboard.scss'
})
export class AdminDashboard implements OnInit {
  accounts: AccountDTO[] = [];
  transactions: TransactionDTO[] = [];
  error: string = '';

  constructor(
    private accountService: AccountService,
    private transactionService: TransactionService
  ) {}

  ngOnInit() {
    this.accountService.getAllAccounts().subscribe({
      next: accounts => this.accounts = accounts,
      error: err => this.error = 'Failed to load accounts.'
    });

    this.transactionService.getAllTransactions().subscribe({
      next: txs => this.transactions = txs,
      error: err => this.error = 'Failed to load transactions.'
    });
  }
}