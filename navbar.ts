import { CommonModule } from '@angular/common';
import { Component, Input, Output, EventEmitter } from '@angular/core';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-navbar',
  standalone: true,
  templateUrl: './navbar.html',
  styleUrl: './navbar.scss',
  imports: [CommonModule, RouterLink]
})
export class Navbar {
  @Input() isLoggedIn: boolean = false;
  @Input() userRole: string | null = null;
  @Input() username: string = '';
  @Input() changeView!: (view: 'home' | 'login' | 'register' | 'dashboard') => void;
  @Output() logout = new EventEmitter<void>();

  dropdownOpen = false;

  toggleDropdown() {
    this.dropdownOpen = !this.dropdownOpen;
  }

  ngOnChanges() {
    console.log('isLoggedIn input value:', this.isLoggedIn);
  }

  closeDropdown() {
    this.dropdownOpen = false;
  }
}