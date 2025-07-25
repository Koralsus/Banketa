import { Component, Output, EventEmitter } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { AuthService, RegistrationData, AuthResponse } from '../../services/auth.service';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './register.html',
  styleUrl: './register.scss'
})
export class Register {
  username = '';
  email = '';
  password = '';
  firstname = '';
  middlename = '';
  lastname = '';
  loading = false;
  error: string | null = null;

  @Output() registerSuccess = new EventEmitter<{ username: string, token: string }>();
  @Output() cancel = new EventEmitter<void>();

    constructor(private authService: AuthService) {}

  onSubmit() {
    this.error = null;
    this.loading = true;
    const registrationData: RegistrationData = {
      username: this.username,
      email: this.email,
      password: this.password,
      firstname: this.firstname,
      middlename: this.middlename,
      lastname: this.lastname
    };

    this.authService.register(registrationData).subscribe({
      next: (data: AuthResponse) => {
        this.registerSuccess.emit({ username: this.username, token: data.token });
        this.loading = false;
      },
      error: (err) => {
        if (err.error) {
          if (typeof err.error === 'string') {
            this.error = err.error;
          } else if (typeof err.error === 'object') {
            this.error = Object.values(err.error).join(', ');
          } else {
            this.error = 'Registration failed';
          }
        } else {
          this.error = 'Network/server error';
        }
        this.loading = false;
      }
    });
  }
}