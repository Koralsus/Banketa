import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserService, UserDTO } from '../../services/user.service';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-user-profile',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './user-profile.html',
  styleUrl: './user-profile.scss'
})
export class UserProfileComponent implements OnInit {
  user: UserDTO | null = null;
  editMode: boolean = false;
  saving: boolean = false;
  error: string = '';

  constructor(private userService: UserService) {}

  ngOnInit() {
    this.userService.getCurrentUser().subscribe({
      next: (user) => this.user = { ...user },
      error: (err) => this.error = 'Failed to load user data.'
    });
  }

  enableEdit() {
    this.editMode = true;
  }

  cancelEdit() {
    this.editMode = false;
    this.ngOnInit();
  }

  save() {
    if (this.user) {
      this.saving = true;
      this.userService.updateUser(this.user).subscribe({
        next: (updated) => {
          this.user = updated;
          this.editMode = false;
          this.saving = false;
        },
        error: (err) => {
          this.error = 'Failed to save changes.';
          this.saving = false;
        }
      });
    }
  }
}