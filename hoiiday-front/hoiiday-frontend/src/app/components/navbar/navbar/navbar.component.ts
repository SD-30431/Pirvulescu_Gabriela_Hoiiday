import { Component, OnInit } from '@angular/core';
import { LoginComponent } from '../../login/login.component';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../../services/auth.service';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [LoginComponent, CommonModule],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css',
})
export class NavbarComponent implements OnInit {
  showLogin = false;
  isLoggedIn = false;
  firstName: string | null = null;
  lastName: string | null = null;

  constructor(private authService: AuthService) {}

  ngOnInit(): void {
    this.authService.isAuthenticated().subscribe((loggedIn: boolean) => {
      this.isLoggedIn = loggedIn;
      if (this.isLoggedIn) {
        this.firstName = this.authService.getFirstName();
        this.lastName = this.authService.getLastName();
      }
    });
  }

  openLogin(): void {
    this.showLogin = true;
  }

  closeLogin(): void {
    this.showLogin = false;
  }
}