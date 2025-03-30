import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private loggedIn = new BehaviorSubject<boolean>(false);
  private userFirstName: string | null = null;
  private userLastName: string | null = null;

  isAuthenticated(): Observable<boolean> {
    return this.loggedIn.asObservable();
  }

  setLoggedIn(status: boolean): void {
    this.loggedIn.next(status);
  }

  setUserInfo(firstName: string, lastName: string): void {
    this.userFirstName = firstName;
    this.userLastName = lastName;
  }

  getFirstName(): string | null {
    return this.userFirstName;
  }

  getLastName(): string | null {
    return this.userLastName;
  }
}