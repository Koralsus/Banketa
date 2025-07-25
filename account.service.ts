import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AccountStatus, AccountType } from '../models/account.enums';
import { UserRole } from '../models/user.enums';


export interface UserDTO {
  userId: number;
  username: string;
  email: string;
  firstname: string;
  middlename?: string;
  lastname: string;
  createdAt: string;
  role: UserRole; 
}

export interface AccountDTO {
  accountNumber: string;
  balance: number;
  accountType: AccountType;
  accountStatus: AccountStatus;
  createdAt: string;
  user: UserDTO;
}

@Injectable({ providedIn: 'root' })
export class AccountService {
  private baseUrl = '/api/account';

  constructor(private http: HttpClient) {}

  getAllAccounts(): Observable<AccountDTO[]> {
    return this.http.get<AccountDTO[]>(`${this.baseUrl}`);
  }

  getAccountById(id: number): Observable<AccountDTO> {
    return this.http.get<AccountDTO>(`${this.baseUrl}/${id}`);
  }

  getMyAccounts(): Observable<AccountDTO[]> {
    return this.http.get<AccountDTO[]>(`${this.baseUrl}/my`);
  }

  createAccount(account: AccountDTO): Observable<AccountDTO> {
    return this.http.post<AccountDTO>(`${this.baseUrl}`, account);
  }

  updateAccount(id: number, account: AccountDTO): Observable<AccountDTO> {
    return this.http.put<AccountDTO>(`${this.baseUrl}/${id}`, account);
  }

  deleteAccount(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }

  searchByAccountNumber(accountNumber: string): Observable<AccountDTO> {
    return this.http.get<AccountDTO>(`${this.baseUrl}/search/accountnumber?accountNumber=${accountNumber}`);
  }

  searchByAccountType(type: AccountType): Observable<AccountDTO[]> {
    return this.http.get<AccountDTO[]>(`${this.baseUrl}/search/type?type=${type}`);
  }

  searchByAccountStatus(status: AccountStatus): Observable<AccountDTO[]> {
    return this.http.get<AccountDTO[]>(`${this.baseUrl}/search/status?status=${status}`);
  }

  searchByCreationDate(creation: string): Observable<AccountDTO[]> {
    return this.http.get<AccountDTO[]>(`${this.baseUrl}/search/creationdate?creation=${creation}`);
  }

  searchByCreationBefore(before: string): Observable<AccountDTO[]> {
    return this.http.get<AccountDTO[]>(`${this.baseUrl}/search/creationdate/before?before=${before}`);
  }

  searchByCreationAfter(after: string): Observable<AccountDTO[]> {
    return this.http.get<AccountDTO[]>(`${this.baseUrl}/search/creationdate/after?after=${after}`);
  }

  searchByCreationBetween(start: string, end: string): Observable<AccountDTO[]> {
    return this.http.get<AccountDTO[]>(`${this.baseUrl}/search/creationdate/between?start=${start}&end=${end}`);
  }
}