import { Component, EventEmitter, Output } from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-transaction-form',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './transaction-form.html',
  styleUrls: ['./transaction-form.scss']
})
export class TransactionFormComponent {
  recipient = '';
  amount: number | null = null;

  @Output() transfer = new EventEmitter<{ recipient: string, amount: number }>();

  submit() {
    if (this.recipient && this.amount && this.amount > 0) {
      this.transfer.emit({
        recipient: this.recipient,
        amount: this.amount
      });
      this.recipient = '';
      this.amount = null;
    }
  }
}