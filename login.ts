import { Component, Output, EventEmitter } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { AuthService, LoginData, AuthResponse } from '../../services/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './login.html',
  styleUrl: './login.scss'
})
export class Login {
  usernameOrEmail: string = '';
  password: string = '';
  loading = false;
  error: string | null = null;

  @Output() login = new EventEmitter<{ username: string, token: string, role: string }>();
  @Output() showRegister = new EventEmitter<void>();

  constructor(private authService: AuthService) {}

  onSubmit() {
    this.error = null;
    this.loading = true;
    const loginData: LoginData = {
      usernameOrEmail: this.usernameOrEmail,
      password: this.password
    };

    this.authService.login(loginData).subscribe({
      next: (data: AuthResponse) => {
        localStorage.setItem('token', data.token);
        this.authService.getProfile().subscribe({
          next: (profile) => {
            this.login.emit({
              username: profile.username,
              token: data.token,
              role: profile.role
            });
            this.loading = false;
          },
          error: () => {
            this.error = 'Could not fetch user profile';
            this.loading = false;
          }
        });
      },
      error: () => {
        this.error = 'Invalid credentials or server error';
        this.loading = false;
      }
    });
  }
}