import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ChatService } from '../../services/chat.service';

@Component({
  selector: 'app-chat',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.css']
})
export class ChatComponent {
  history: { from: 'user'|'bot', text: string }[] = [];
  input = '';
  isOpen = false;
  isLoading = false;

  constructor(private chat: ChatService) {}

  toggle() {
    this.isOpen = !this.isOpen;
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