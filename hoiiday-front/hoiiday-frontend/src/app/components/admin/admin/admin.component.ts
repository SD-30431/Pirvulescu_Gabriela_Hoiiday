// admin.component.ts
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserComponent } from '../../user/user/user.component';
import { ClientService } from '../../../services/client.service'; // Import ClientService
import { User } from '../../../model/user.model';

@Component({
  selector: 'app-admin',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css'],
})
export class AdminComponent implements OnInit {
  clients: User[] = []; 
  errorMessage: string = '';

  constructor(private clientService: ClientService) {}

  ngOnInit(): void {
    this.getClients(); 
  }

  getClients(): void {
    this.clientService.getClients().subscribe(
      (data) => {
        this.clients = data;
        console.log('Clients loaded:', data);
      },
      (error) => console.error('Error loading clients:', error)
    );
  }

  deleteClient(userId: number): void {
    this.clientService.deleteClient(userId).subscribe(
      () => {
        console.log(`Client with ID ${userId} deleted.`);
        this.getClients();
      },
      (error) => console.error('Error deleting client:', error)
    );
  }

  // updateClient(client: User): void {
  //   this.clientService.updateClient(client).subscribe(
  //     (updatedClient) => {
  //       console.log('Client updated:', updatedClient);
  //       this.getClients(); 
  //     },
  //     (error) => console.error('Error updating client:', error)
  //   );
  // }
}