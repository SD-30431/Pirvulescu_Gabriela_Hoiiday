import { Component, OnInit, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DarkModeService } from '../../services/dark-mode.service';
import { Subscription } from 'rxjs';
import { ChatToggleService } from '../../services/chat-toggle.service';

@Component({
  selector: 'app-shortcut-hint',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './shortcut-hint.component.html',
  styleUrl: './shortcut-hint.component.css',
})
export class ShortcutHintComponent implements OnInit, OnDestroy {
  visible = false;
  showDarkModeHint = false;
  showChatHint = false;
  private darkModeSubscription!: Subscription;
  private chatToggleSubscription!: Subscription;
  private timeout: any;

  constructor(
    private darkModeService: DarkModeService,
    private chatToggleService: ChatToggleService
  ) {}

  ngOnInit(): void {
    // Subscribe to dark mode changes
    this.darkModeSubscription = this.darkModeService.darkMode$.subscribe(() => {
      this.showDarkModeHint = true;
      this.showChatHint = false;
      this.showHint();
    });

    // Subscribe to chat toggle changes
    this.chatToggleSubscription = this.chatToggleService.chatOpen$.subscribe(() => {
      this.showDarkModeHint = false;
      this.showChatHint = true;
      this.showHint();
    });
  }

  ngOnDestroy(): void {
    if (this.darkModeSubscription) {
      this.darkModeSubscription.unsubscribe();
    }
    if (this.chatToggleSubscription) {
      this.chatToggleSubscription.unsubscribe();
    }
    if (this.timeout) {
      clearTimeout(this.timeout);
    }
  }

  private showHint(): void {
    this.visible = true;
    
    // Clear any existing timeout
    if (this.timeout) {
      clearTimeout(this.timeout);
    }
    
    // Hide after 1 second
    this.timeout = setTimeout(() => {
      this.visible = false;
      // Reset hints after hiding
      setTimeout(() => {
        this.showDarkModeHint = false;
        this.showChatHint = false;
      }, 300); // Wait for fade out animation
    }, 500);
  }
}