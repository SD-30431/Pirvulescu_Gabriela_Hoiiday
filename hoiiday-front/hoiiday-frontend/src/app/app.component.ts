import { Component, OnInit } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';
import { UserService } from './services/user.service';
import { User } from './model/user.model';
import { NavbarComponent } from './components/navbar/navbar/navbar.component';
import { ChatComponent } from "./components/chat/chat.component";


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
  standalone: true,
  imports: [RouterOutlet, ChatComponent, NavbarComponent]
})

export class AppComponent implements OnInit {
  title = 'hoiiday-frontend';
  public users: User[] = [];

  constructor(private userService: UserService) {}

  ngOnInit() {
    this.getUsers();
  }


  public getUsers(): void {
    this.userService.getAllUsers().subscribe(
      (response: User[]) => {
        this.users = response;
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }
}
