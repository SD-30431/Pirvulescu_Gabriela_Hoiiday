import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ClientService } from '../../../services/client.service';
import { User } from '../../../model/user.model';
import { Role } from '../../../model/role.enum';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-admin',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css'],
})
export class AdminComponent implements OnInit {
  clients: User[] = [];
  errorMessage: string = '';
  showUpdateModal = false;
  showCreateModal = false;
  selectedClient: User = new User(0, '', '', '', '', Role.CLIENT);

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

  openUpdateModal(client: User): void {
    this.selectedClient = { ...client };
    this.showUpdateModal = true;
  }

  openCreateModal(): void {
    console.log("openCreateModal() called!"); // Add this line
    this.selectedClient = new User(0, '', '', '', '', Role.CLIENT);
    this.showCreateModal = true;
  }

  closeModal(): void {
    this.showUpdateModal = false;
    this.showCreateModal = false;
  }

  updateClient(): void {
    this.clientService.updateClient(this.selectedClient).subscribe(
      (updatedClient) => {
        console.log('Client updated:', updatedClient);
        this.getClients();
        this.closeModal();
      },
      (error) => console.error('Error updating client:', error)
    );
  }

  createUser(): void {
    console.log("createUser() called!"); // Add this line
    this.clientService.createClient(this.selectedClient).subscribe(
      (newUser) => {
        console.log('User created:', newUser);
        this.getClients();
        this.closeModal();
      },
      (error) => console.error('Error creating user:', error)
    );
  }
}