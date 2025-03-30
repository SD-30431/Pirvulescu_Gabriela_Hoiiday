import { Component, EventEmitter, Output } from '@angular/core';
import { HttpClient, HttpClientModule } from '@angular/common/http'; // Import HttpClientModule
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [HttpClientModule, FormsModule], // Add HttpClientModule to imports
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})

export class LoginComponent {
  email = '';
  password = '';
  rememberMe = false;

  @Output() close = new EventEmitter<void>();

  constructor(private http: HttpClient, private router: Router) {}

  login() {
    console.log('Login button clicked');
    console.log('Email:', this.email);
    console.log('Password:', this.password);

    const loginData = {
      email: this.email,
      password: this.password
    };

    this.http.post<any>('http://localhost:8080/api/auth/login', loginData).subscribe(
      response => {
        console.log('Login response:', response);
        if (response.success) {
          if (response.role === 'ADMIN') {
            this.router.navigate(['/admin']);
          } else {
            this.router.navigate(['/client']);
          }
        } else {
          alert(response.message);
        }
      },
      error => {
        console.error('Login error:', error);
        alert('Login failed');
      }
    );

    this.email = '';
    this.password = '';
    this.rememberMe = false;
  }
  navigateToSignUp() {
    console.log("Navigating to sign up");
    // this.router.navigate(['/signup']);
  }

  navigateToForgotPassword() {
    console.log("Navigating to forgot password");
    // this.router.navigate(['/forgot-password']);
  }

  closeLogin() {
    this.close.emit();
  }
}