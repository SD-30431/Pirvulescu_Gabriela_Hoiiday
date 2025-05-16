import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginComponent } from '../../login/login.component';
import { Router }       from '@angular/router';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [CommonModule, LoginComponent],   
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css'],   
})
export class NavbarComponent {
  showLogin   = false;
  isLoggedIn  = false;
  firstName: string | null = null;
  lastName:  string | null = null;

  constructor(private router:Router){}

  openLogin()  { this.showLogin = true; }
  closeLogin() { this.showLogin = false; }

  setUserInfo(fn: string, ln: string) {
    this.isLoggedIn = true;
    this.firstName  = fn;
    this.lastName   = ln;
    this.showLogin  = false;
  }

  logout() {
    this.isLoggedIn = false;
    this.firstName  = null;
    this.lastName   = null;

    this.router.navigate(['/client']);
  }
}
