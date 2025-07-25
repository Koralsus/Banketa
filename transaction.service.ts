import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AccountDTO } from './account.service';
import { TransactionType } from '../models/transaction.enums';


export interface TransactionDTO {
  transactionId?: number;
  fromAccount?: AccountDTO;
  toAccount?: AccountDTO;
  amount: number;
  transactionDate?: string;
  transactionType: TransactionType;
  remarks?: string;
}

@Injectable({ providedIn: 'root' })
export class TransactionService {
  private baseUrl = '/api/transaction';

  constructor(private http: HttpClient) {}

  getAllTransactions(): Observable<TransactionDTO[]> {
    return this.http.get<TransactionDTO[]>(`${this.baseUrl}`);
  }

  getMyTransactions(): Observable<TransactionDTO[]> {
    return this.http.get<TransactionDTO[]>(`${this.baseUrl}/my`);
  }

  getMyTransactionsByType(type: TransactionType): Observable<TransactionDTO[]> {
    return this.http.get<TransactionDTO[]>(`${this.baseUrl}/my/type?type=${type}`);
  }

  getMyFromTransactions(): Observable<TransactionDTO[]> {
    return this.http.get<TransactionDTO[]>(`${this.baseUrl}/my/from`);
  }

  getMyToTransactions(): Observable<TransactionDTO[]> {
    return this.http.get<TransactionDTO[]>(`${this.baseUrl}/my/to`);
  }

  createTransaction(transaction: TransactionDTO): Observable<TransactionDTO> {
    return this.http.post<TransactionDTO>(`${this.baseUrl}`, transaction);
  }

  searchByTransactionDate(date: string): Observable<TransactionDTO[]> {
    return this.http.get<TransactionDTO[]>(`${this.baseUrl}/search/transactiondate?date=${date}`);
  }

  searchByTransactionType(type: TransactionType): Observable<TransactionDTO[]> {
    return this.http.get<TransactionDTO[]>(`${this.baseUrl}/search/type?type=${type}`);
  }

  searchByAmountGreaterThan(amount: number): Observable<TransactionDTO[]> {
    return this.http.get<TransactionDTO[]>(`${this.baseUrl}/search/amount/greaterthan?amount=${amount}`);
  }

  searchByAmountLessThan(amount: number): Observable<TransactionDTO[]> {
    return this.http.get<TransactionDTO[]>(`${this.baseUrl}/search/amount/lessthan?amount=${amount}`);
  }

  searchByTransactionDateBetween(start: string, end: string): Observable<TransactionDTO[]> {
    return this.http.get<TransactionDTO[]>(`${this.baseUrl}/search/transactiondate/between?start=${start}&end=${end}`);
  }

  searchByFromAccountNumber(accountNumber: string): Observable<TransactionDTO[]> {
    return this.http.get<TransactionDTO[]>(`${this.baseUrl}/search/fromaccount?accountNumber=${accountNumber}`);
  }

  searchByToAccountNumber(accountNumber: string): Observable<TransactionDTO[]> {
    return this.http.get<TransactionDTO[]>(`${this.baseUrl}/search/toaccount?accountNumber=${accountNumber}`);
  }

  searchByFromAccountNumberAndDateRange(accountNumber: string, start: string, end: string): Observable<TransactionDTO[]> {
    return this.http.get<TransactionDTO[]>(`${this.baseUrl}/search/fromaccount/daterange?accountNumber=${accountNumber}&start=${start}&end=${end}`);
  }

  searchByToAccountNumberAndDateRange(accountNumber: string, start: string, end: string): Observable<TransactionDTO[]> {
    return this.http.get<TransactionDTO[]>(`${this.baseUrl}/search/toaccount/daterange?accountNumber=${accountNumber}&start=${start}&end=${end}`);
  }

  searchByFromOrToAccountNumber(fromAccountNumber: string, toAccountNumber: string): Observable<TransactionDTO[]> {
    return this.http.get<TransactionDTO[]>(`${this.baseUrl}/search/fromortoaccount?fromAccountNumber=${fromAccountNumber}&toAccountNumber=${toAccountNumber}`);
  }
}