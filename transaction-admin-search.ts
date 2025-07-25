import { Component } from '@angular/core';
import { TransactionService, TransactionDTO } from '../../services/transaction.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { TransactionType } from '../../models/transaction.enums';

@Component({
  selector: 'app-transaction-admin-search',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './transaction-admin-search.html',
  styleUrl: './transaction-admin-search.scss'
})
export class TransactionAdminSearchComponent {
  typeQuery: TransactionType | null = null;
  dateQuery: Date | null = null;
  amountQuery: number | null = null;
  transactions: TransactionDTO[] = [];
  error = '';

  constructor(private transactionService: TransactionService) {}

  searchByType() {
    if (!this.typeQuery) return;
    this.transactionService.searchByTransactionType(this.typeQuery)
      .subscribe({
        next: txs => { this.transactions = txs; this.error = ''; },
        error: err => { this.error = 'No transactions found.'; this.transactions = []; }
      });
  }

  searchByDate() {
    if (!this.dateQuery) return;
    const dateStr = this.dateQuery.toISOString().slice(0, 10);
    this.transactionService.searchByTransactionDate(dateStr)
      .subscribe({
        next: txs => { this.transactions = txs; this.error = ''; },
        error: err => { this.error = 'No transactions found.'; this.transactions = []; }
      });
  }

  searchByAmountGreaterThan() {
    if (this.amountQuery === null) return;
    this.transactionService.searchByAmountGreaterThan(this.amountQuery)
      .subscribe({
        next: txs => { this.transactions = txs; this.error = ''; },
        error: err => { this.error = 'No transactions found.'; this.transactions = []; }
      });
  }

  searchByAmountLessThan() {
    if (this.amountQuery === null) return;
    this.transactionService.searchByAmountLessThan(this.amountQuery)
    .subscribe({
      next: txs => { this.transactions = txs; this.error ='';},
      error: err => { this.error = 'No transactions found.'; this.transactions = []; }
    })
  }
}