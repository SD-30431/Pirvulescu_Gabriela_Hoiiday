import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ChatToggleService {
  private chatOpenSubject = new BehaviorSubject<boolean>(false);
  chatOpen$ = this.chatOpenSubject.asObservable();

  constructor() {}

  toggleChat(): void {
    this.chatOpenSubject.next(!this.chatOpenSubject.value);
  }

  openChat(): void {
    this.chatOpenSubject.next(true);
  }

  closeChat(): void {
    this.chatOpenSubject.next(false);
  }

  get isChatOpen(): boolean {
    return this.chatOpenSubject.value;
  }
}