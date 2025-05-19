import { Component, EventEmitter, Output } from '@angular/core';
import { AuthService, LoginSuccessPayload } from '../../services/auth.service';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  email = '';
  password = '';
  rememberMe = false;
  loading = false;
  error = '';

  @Output() close = new EventEmitter<void>();
  @Output() loginSuccess = new EventEmitter<LoginSuccessPayload>();

  constructor(
    private auth: AuthService,
    private router: Router
  ) {}

  login() {
    this.loading = true;
    this.error = '';
    
    this.auth.attemptLogin(this.email, this.password)
      .subscribe({
        next: payload => {
          this.loginSuccess.emit(payload);
          // redirect
          if (payload.role === 'ADMIN') {
            this.router.navigate(['/admin']);
          } else {
            this.router.navigate(['/client']);
          }
          this.close.emit();
        },
        error: err => {
          this.error = err.error?.message || 'Login failed';
          this.loading = false;
        },
        complete: () => {
          this.loading = false;
        }
      });
  }

  closeLogin() {
    this.close.emit();
  }
}