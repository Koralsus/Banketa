import { Component } from '@angular/core';
import { UserService, UserDTO } from '../../services/user.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-user-admin-search',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './user-admin-search.html',
  styleUrl: './user-admin-search.scss'
})
export class UserAdminSearchComponent {
  usernameQuery = '';
  emailQuery = '';
  firstnameQuery = '';
  lastnameQuery = '';
  creationDateQuery: Date | null = null;
  users: UserDTO[] = [];
  error = '';

  constructor(private userService: UserService) {}

  searchByUsername() {
    if (!this.usernameQuery) return;
    this.userService.searchByUsername(this.usernameQuery)
      .subscribe({
        next: users => { this.users = users; this.error = ''; },
        error: err => { this.error = 'No users found.'; this.users = []; }
      });
  }

  searchByEmail() {
    if (!this.emailQuery) return;
    this.userService.searchByEmail(this.emailQuery)
      .subscribe({
        next: user => { this.users = user ? [user] : []; this.error = ''; },
        error: err => { this.error = 'No user found.'; this.users = []; }
      });
  }

  searchByFirstname() {
    if (!this.firstnameQuery) return;
    this.userService.searchByFirstname(this.firstnameQuery)
      .subscribe({
        next: users => { this.users = users; this.error = ''; },
        error: err => { this.error = 'No users found.'; this.users = []; }
      });
  }

  searchByLastname() {
    if (!this.lastnameQuery) return;
    this.userService.searchByLastname(this.lastnameQuery)
      .subscribe({
        next: users => { this.users = users; this.error = ''; },
        error: err => { this.error = 'No users found.'; this.users = []; }
      });
  }

  searchByCreationDate() {
    if (!this.creationDateQuery) return;
    const dateStr = this.creationDateQuery.toISOString().slice(0, 10);
    this.userService.searchByCreationDate(dateStr)
      .subscribe({
        next: users => { this.users = users; this.error = ''; },
        error: err => { this.error = 'No users found.'; this.users = []; }
      });
  }
}