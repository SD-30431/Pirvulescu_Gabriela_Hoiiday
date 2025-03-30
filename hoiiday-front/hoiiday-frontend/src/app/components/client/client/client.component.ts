import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../../services/auth.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-client',
  imports:[CommonModule,FormsModule],
  templateUrl: './client.component.html',
  styleUrls: ['./client.component.css']
})
export class ClientComponent implements OnInit {
  firstName: string | null = null;
  lastName: string | null = null;

  constructor(private authService: AuthService) {}

  ngOnInit(): void {
    this.firstName = this.authService.getFirstName();
    this.lastName = this.authService.getLastName();
  }
}