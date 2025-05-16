import { Component, ViewEncapsulation, type OnInit } from "@angular/core"
import { CommonModule } from "@angular/common"
import { FormsModule } from "@angular/forms"
import { User } from "../../../model/user.model"
import { Role } from "../../../model/role.enum"
import { ClientCardComponent } from "../../client/client/client-card.component"
import { ClientService } from "../../../services/client.service"


@Component({
  selector: "app-admin",
  standalone: true,
  imports: [CommonModule, FormsModule, ClientCardComponent],
  encapsulation: ViewEncapsulation.None,
  templateUrl: "./admin.component.html",
  styleUrls: ["./admin.component.css"],
})
export class AdminComponent implements OnInit {
  clients: User[] = []
  filteredClients: User[] = []
  allClients: User[] = []
  selectedClient: User = new User(0, "", "", "", "", Role.CLIENT, "")
  errorMessage = ""
  showCreateModal = false
  showUpdateModal = false
  viewMode: "list" | "card" = "list"
  searchTerm = ""

  notificationCount = 0 

  properties: any[] = []
  selectedProperties: number[] = []

  sortDirection: "asc" | "desc" = "asc"
  sortField = "firstName"

  constructor(private clientService: ClientService) {}

  ngOnInit(): void {
    this.fetchClients()
    this.fetchNotifications()
  }

  fetchClients(): void {
    this.clientService.getClients().subscribe(
      (data) => {
        this.allClients = [...data] 
        this.clients = data
        this.filteredClients = data
      },
      (error) => {
        this.errorMessage = "Failed to load clients. Please try again."
        console.error("Error fetching clients:", error)
      },
    )
  }

  fetchNotifications(): void {

    setTimeout(() => {
      this.notificationCount = Math.floor(Math.random() * 5) 
    }, 1000)
  }

  openCreateModal(): void {
    this.selectedClient = new User(0, "", "", "", "", Role.CLIENT, "")
    this.showCreateModal = true
  }

  openUpdateModal(client: User): void {
    this.selectedClient = new User(
      client.userId,
      client.firstName,
      client.lastName,
      client.email,
      client.phoneNumber,
      client.role,
      "",
    )
    this.showUpdateModal = true
  }

  closeModal(): void {
    this.showCreateModal = false
    this.showUpdateModal = false
  }

  createUser(): void {
    this.clientService.createClient(this.selectedClient).subscribe(
      (response) => {
        this.fetchClients()
        this.closeModal()
      },
      (error) => {
        this.errorMessage = "Failed to create client. Please try again."
        console.error("Error creating client:", error)
      },
    )
  }

  updateClient(): void {
    this.clientService.updateClient(this.selectedClient).subscribe(
      (response) => {
        this.fetchClients()
        this.closeModal()
      },
      (error) => {
        this.errorMessage = "Failed to update client. Please try again."
        console.error("Error updating client:", error)
      },
    )
  }

  deleteClient(userId: number): void {
    if (confirm("Are you sure you want to delete this client?")) {
      this.clientService.deleteClient(userId).subscribe(
        (response) => {
          this.fetchClients()
        },
        (error) => {
          this.errorMessage = "Failed to delete client. Please try again."
          console.error("Error deleting client:", error)
        },
      )
    }
  }

  viewClientDetails(client: User): void {
    // Navigate to client details page or show details modal
    console.log("View client details:", client)
  }

  setViewMode(mode: "list" | "card"): void {
    this.viewMode = mode
  }

  searchClients(event: Event): void {
    const target = event.target as HTMLInputElement
    this.searchTerm = target.value.toLowerCase().trim()

    if (!this.searchTerm) {

      this.clients = [...this.allClients]
    } else {

      this.clients = this.allClients.filter(
        (client) =>
          client.firstName.toLowerCase().includes(this.searchTerm) ||
          client.lastName.toLowerCase().includes(this.searchTerm) ||
          `${client.firstName} ${client.lastName}`.toLowerCase().includes(this.searchTerm) ||
          client.email.toLowerCase().includes(this.searchTerm),
      )
    }

    if (this.sortDirection) {
      this.sortClientsAlphabetically(false)
    }
  }

  sortClientsAlphabetically(toggleDirection = true): void {
    if (toggleDirection) {
      this.sortDirection = this.sortDirection === "asc" ? "desc" : "asc"
    }

    this.clients.sort((a, b) => {
      const nameA = `${a.firstName} ${a.lastName}`.toLowerCase()
      const nameB = `${b.firstName} ${b.lastName}`.toLowerCase()

      if (this.sortDirection === "asc") {
        return nameA.localeCompare(nameB)
      } else {
        return nameB.localeCompare(nameA)
      }
    })
  }
}

