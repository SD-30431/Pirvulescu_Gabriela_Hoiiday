// src/app/app.component.ts
import {
  Component,
  OnInit,
  OnDestroy,
  Inject,
  PLATFORM_ID,
  HostListener
} from '@angular/core';
import { isPlatformBrowser } from '@angular/common';
import { Subscription }      from 'rxjs';
import { DarkModeService }   from './services/dark-mode.service';
import { ChatToggleService } from './services/chat-toggle.service';
import { UserService }       from './services/user.service';
import { User }              from './model/user.model';
import { NavbarComponent } from "./components/navbar/navbar/navbar.component";
import { ShortcutHintComponent } from "./components/shortcut/shortcut-hint.component";
import { ChatComponent } from "./components/chat/chat.component";
import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  imports: [NavbarComponent, ShortcutHintComponent, ChatComponent, RouterOutlet],
})
export class AppComponent implements OnInit, OnDestroy {
  public users: User[] = [];
  private darkModeSub?: Subscription;

  constructor(
    private userService: UserService,
    private darkModeService: DarkModeService,
    private chatToggleService: ChatToggleService,
    @Inject(PLATFORM_ID) private platformId: Object
  ) {}

  ngOnInit() {
    // server-side skip
    if (isPlatformBrowser(this.platformId)) {
      this.getUsers();

      // only toggle body class in browser
      this.darkModeSub = this.darkModeService.darkMode$.subscribe(isDark => {
        document.body.classList.toggle('dark-mode', isDark);
      });
    }
  }

  ngOnDestroy() {
    this.darkModeSub?.unsubscribe();
  }

  @HostListener('window:keydown', ['$event'])
  handleKeyDown(event: KeyboardEvent) {
    if (!isPlatformBrowser(this.platformId)) return;

    if (event.shiftKey && (event.key === 'D' || event.key === 'd')) {
      this.darkModeService.toggleDarkMode();
      event.preventDefault();
    }
    if (event.shiftKey && (event.key === 'C' || event.key === 'c')) {
      this.chatToggleService.toggleChat();
      event.preventDefault();
    }
  }

  private getUsers(): void {
    this.userService.getAllUsers().subscribe({
      next: (users) => this.users = users,
      error: err => {
        // only alert in browser
        if (isPlatformBrowser(this.platformId)) {
          alert(err.message);
        } else {
          console.error('User load failed on server:', err);
        }
      }
    });
  }
}
