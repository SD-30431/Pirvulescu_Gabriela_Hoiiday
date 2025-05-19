import { Component, OnInit, OnDestroy, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { Subscription } from 'rxjs';
import { DarkModeToggleComponent } from '../../dark-mode/dark-mode-toggle.component';
import { DarkModeService } from '../../../services/dark-mode.service';
import { LoginComponent } from "../../login/login.component";


@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [CommonModule, RouterModule, DarkModeToggleComponent, LoginComponent],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css'
})
export class NavbarComponent implements OnInit, OnDestroy {
  isLoggedIn = false;
  firstName = '';
  lastName = '';
  showLogin = false;
  isDarkMode = false;
  
  private darkModeService = inject(DarkModeService);
  private darkModeSubscription?: Subscription; // Change to optional property
  
  ngOnInit(): void {
    // Subscribe to dark mode changes
    this.darkModeSubscription = this.darkModeService.darkMode$.subscribe(
      isDark => {
        this.isDarkMode = isDark;
      }
    );
    
    // Set initial dark mode state
    this.isDarkMode = this.darkModeService.isCurrentlyDarkMode;
  }
  
  openLogin(): void {
    this.showLogin = true;
  }
  
  closeLogin(): void {
    this.showLogin = false;
  }
  
  setUserInfo(firstName: string, lastName: string): void {
    this.firstName = firstName;
    this.lastName = lastName;
    this.isLoggedIn = true;
    this.showLogin = false;
  }
  
  logout(): void {
    this.isLoggedIn = false;
    this.firstName = '';
    this.lastName = '';
  }
  
  ngOnDestroy(): void {
    // Use optional chaining to safely unsubscribe
    this.darkModeSubscription?.unsubscribe();
  }
}