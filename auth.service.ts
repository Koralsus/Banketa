import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface RegistrationData {
  username: string;
  email: string;
  password: string;
  firstname: string;
  middlename?: string;
  lastname: string;
}

export interface LoginData {
  usernameOrEmail: string;
  password: string;
}

export interface AuthResponse {
  token: string;
}

export interface UserProfile {
  username: string;
  role: string;
}

@Injectable({ providedIn: 'root' })
export class AuthService {
  private apiUrl = 'http://localhost:8080/api/auth';
  private userUrl = 'http://localhost:8080/api/user/me';

  constructor(private http: HttpClient) {}

  register(data: RegistrationData): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.apiUrl}/register`, data);
  }

  login(data: LoginData): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.apiUrl}/login`, data);
  }

  getProfile(): Observable<UserProfile> {
    return this.http.get<UserProfile>(this.userUrl);
  }
}