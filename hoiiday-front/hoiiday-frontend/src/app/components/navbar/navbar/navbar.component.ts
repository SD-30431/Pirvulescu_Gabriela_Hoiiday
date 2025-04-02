import { Component } from '@angular/core';
import { LoginComponent } from '../../login/login.component';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [LoginComponent, CommonModule],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css',
})
export class NavbarComponent {
  showLogin = false;
  isLoggedIn = false;
  firstName: string | null = null;
  lastName: string | null = null;

  constructor() {}

  openLogin(): void {
    this.showLogin = true;
  }

  closeLogin(): void {
    this.showLogin = false;
  }

  setUserInfo(firstName: string, lastName: string): void {
    this.isLoggedIn = true;
    this.firstName = firstName;
    this.lastName = lastName;
  }

  logout(): void {
    this.isLoggedIn = false;
    this.firstName = null;
    this.lastName = null;
  }
}