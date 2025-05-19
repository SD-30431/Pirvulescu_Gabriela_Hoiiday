import { Component, OnInit, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ChatService } from '../../services/chat.service';
import { ChatToggleService } from '../../services/chat-toggle.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-chat',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.css']
})
export class ChatComponent implements OnInit, OnDestroy {
  history: { from: 'user'|'bot', text: string }[] = [];
  input = '';
  isOpen = false;
  isLoading = false;
  private chatToggleSubscription!: Subscription;

  constructor(
    private chat: ChatService,
    private chatToggleService: ChatToggleService
  ) {}

  ngOnInit(): void {
    // Subscribe to the chat toggle service
    this.chatToggleSubscription = this.chatToggleService.chatOpen$.subscribe(isOpen => {
      this.isOpen = isOpen;
    });
  }

  ngOnDestroy(): void {
    if (this.chatToggleSubscription) {
      this.chatToggleSubscription.unsubscribe();
    }
  }

  toggle() {
    // Use the service to toggle the chat
    this.chatToggleService.toggleChat();
  }

  send() {
    if (!this.input.trim()) return;
    
    const prompt = this.input;
    this.history.push({ from: 'user', text: prompt });
    this.input = '';
    
    this.isLoading = true;
    this.history.push({ from: 'bot', text: '...' });
    
    this.chat.ask(prompt).subscribe({
      next: (reply) => {
        this.history.pop(); 
        this.history.push({ from: 'bot', text: reply });
        this.isLoading = false;
      },
      error: (err) => {
        this.history.pop();
        this.history.push({ 
          from: 'bot', 
          text: 'Sorry, I encountered an error. Please try again later.' 
        });
        this.isLoading = false;
        console.error('Chat error:', err);
      }
    });
  }
}