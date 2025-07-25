import { Component, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Navbar } from './components/navbar/navbar';
import { Login } from './components/login/login';
import { Register } from './components/register/register';
import { CustomerDashboard } from './components/customer-dashboard/customer-dashboard';
import { AdminDashboard } from './components/admin-dashboard/admin-dashboard';
import { Footer } from './components/footer/footer';
import { Home } from './components/home/home';
import { RouterLink } from '@angular/router';

type Role = 'customer' | 'admin' | null;
type View = 'home' | 'login' | 'register' | 'dashboard';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    Navbar,
    Login,
    Register,
    CustomerDashboard,
    AdminDashboard,
    Footer,
    Home
  ],
  templateUrl: './app.html',
  styleUrl: './app.scss'
})
export class App {
  readonly isLoggedIn = signal(false);
  readonly userRole = signal<Role>(null);
  readonly username = signal('');
  readonly view = signal<View>('home');
  readonly message = signal('');

  onLogin({ role, username, token }: { role: 'customer' | 'admin'; username: string; token: string }) {
    this.isLoggedIn.set(true);
    this.userRole.set(role);
    this.username.set(username);
    this.view.set('dashboard');
    this.message.set('');
    // Optionally, store token in localStorage/sessionStorage here
  }

  changeView(view: View) {
  this.view.set(view);
}

  onShowRegister() {
    this.view.set('register');
  }

  onRegisterSuccess({ username, token }: { username: string, token: string }) {
    this.view.set('login');
    this.message.set('Registration successful! Please login.');
  }

  logout() {
    this.isLoggedIn.set(false);
    this.userRole.set(null);
    this.username.set('');
    this.view.set('login');
  }

  onRegisterCancel() {
    this.view.set('login');
  }
}