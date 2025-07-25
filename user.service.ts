import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
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

@Injectable({ providedIn: 'root' })
export class UserService {
  private baseUrl = '/api/user';

  constructor(private http: HttpClient) {}

  getCurrentUser(): Observable<UserDTO> {
    return this.http.get<UserDTO>(`${this.baseUrl}/me`);
  }

  updateUser(user: UserDTO): Observable<UserDTO> {
    return this.http.put<UserDTO>(`${this.baseUrl}/me`, user);
  }

  getAllUsers(): Observable<UserDTO[]> {
    return this.http.get<UserDTO[]>(`${this.baseUrl}`);
  }

  getUserById(id: number): Observable<UserDTO> {
    return this.http.get<UserDTO>(`${this.baseUrl}/${id}`);
  }

  deleteUser(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }

  searchByUsername(username: string): Observable<UserDTO[]> {
    return this.http.get<UserDTO[]>(`${this.baseUrl}/search/username?username=${username}`);
  }

  searchByFirstname(firstname: string): Observable<UserDTO[]> {
    return this.http.get<UserDTO[]>(`${this.baseUrl}/search/firstname?firstname=${firstname}`);
  }

  searchByLastname(lastname: string): Observable<UserDTO[]> {
    return this.http.get<UserDTO[]>(`${this.baseUrl}/search/lastname?lastname=${lastname}`);
  }

  searchByEmail(email: string): Observable<UserDTO> {
    return this.http.get<UserDTO>(`${this.baseUrl}/search/email?email=${email}`);
  }

  searchByCreationDate(creation: string): Observable<UserDTO[]> {
    return this.http.get<UserDTO[]>(`${this.baseUrl}/search/creationdate?creation=${creation}`);
  }

  searchByCreationBefore(before: string): Observable<UserDTO[]> {
    return this.http.get<UserDTO[]>(`${this.baseUrl}/search/creationdate/before?before=${before}`);
  }

  searchByCreationAfter(after: string): Observable<UserDTO[]> {
    return this.http.get<UserDTO[]>(`${this.baseUrl}/search/creationdate/after?after=${after}`);
  }

  searchByCreationBetween(start: string, end: string): Observable<UserDTO[]> {
    return this.http.get<UserDTO[]>(`${this.baseUrl}/search/creationdate/between?start=${start}&end=${end}`);
  }
}