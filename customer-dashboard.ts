import { Component, OnInit } from '@angular/core';
import { UserService, UserDTO } from '../../services/user.service';
import { AccountService, AccountDTO } from '../../services/account.service';
import { TransactionService, TransactionDTO, TransactionType } from '../../services/transaction.service';
import { AccountListComponent } from "../account-list/account-list";
import { TransactionFormComponent } from "../transaction-form/transaction-form";
import { CommonModule } from '@angular/common';
import { UserProfileComponent } from "../user-profile/user-profile";

@Component({
  selector: 'app-customer-dashboard',
  standalone: true,
  imports: [AccountListComponent, TransactionFormComponent, CommonModule, UserProfileComponent],
  templateUrl: './customer-dashboard.html',
  styleUrl: './customer-dashboard.scss'
})
export class CustomerDashboard implements OnInit {
  accounts: AccountDTO[] = [];
  transactions: TransactionDTO[] = [];
  totalBalance = 0;
  username: string = '';

  constructor(
    private accountService: AccountService,
    private transactionService: TransactionService,
    private userService: UserService
  ) {}

  ngOnInit() {
    // Get current user
    this.userService.getCurrentUser().subscribe((user: UserDTO) => {
      this.username = user.username;
    });

    // Get accounts
    this.accountService.getMyAccounts().subscribe(accounts => {
      this.accounts = accounts;
      this.totalBalance = accounts.reduce((sum, acc) => sum + acc.balance, 0);
    });

    // Get transactions
    this.transactionService.getMyTransactions().subscribe(txs => {
      this.transactions = txs;
    });
  }

  handleTransfer({ recipient, amount }: { recipient: string, amount: number }) {
    const fromAccount = this.accounts[0];
    if (!fromAccount) return; // Prevent error if accounts is empty

    this.accountService.searchByAccountNumber(recipient).subscribe(toAccount => {
      const transaction: TransactionDTO = {
        fromAccount,
        toAccount,
        amount,
        transactionType: TransactionType.FUND_TRANSFER,
        remarks: `Transfer to ${recipient}`
      };
      this.transactionService.createTransaction(transaction).subscribe(() => {
        // Refresh data after transfer
        this.ngOnInit();
      });
    });
  }
}