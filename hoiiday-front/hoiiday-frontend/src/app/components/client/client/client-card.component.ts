import { Component, Input, Output, EventEmitter } from "@angular/core"
import { CommonModule } from "@angular/common"
import type { User } from "../../../model/user.model"

@Component({
  selector: "app-client-card",
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="client-card">
      <div class="client-card-header">
        <div class="client-avatar">{{ client.firstName.charAt(0) }}{{ client.lastName.charAt(0) }}</div>
        <div class="client-status">
          <span class="status-badge active">Active</span>
        </div>
      </div>
      <div class="client-card-body">
        <h3 class="client-name">{{ client.firstName }} {{ client.lastName }}</h3>
        <p class="client-email">{{ client.email }}</p>
        <p class="client-phone">{{ client.phoneNumber }}</p>
      </div>
      <div class="client-card-footer">
        <button class="btn-icon" (click)="onView()" title="View Details">
          <i class="fas fa-eye"></i>
        </button>
        <button class="btn-icon" (click)="onEdit()" title="Edit Client">
          <i class="fas fa-edit"></i>
        </button>
        <button class="btn-icon delete" (click)="onDelete()" title="Delete Client">
          <i class="fas fa-trash"></i>
        </button>
      </div>
    </div>
  `,
  styles: [
    `
    .client-card {
      background-color: var(--bg-white);
      border-radius: var(--radius-md);
      box-shadow: var(--shadow-sm);
      border: 1px solid var(--border-color);
      overflow: hidden;
      transition: all 0.2s ease;
    }
    
    .client-card:hover {
      box-shadow: var(--shadow-md);
      transform: translateY(-2px);
    }
    
    .client-card-header {
      padding: 1.5rem;
      display: flex;
      justify-content: space-between;
      align-items: flex-start;
      border-bottom: 1px solid var(--border-color);
    }
    
    .client-avatar {
      width: 50px;
      height: 50px;
      border-radius: 50%;
      background-color: var(--primary-light);
      color: var(--text-white);
      display: flex;
      align-items: center;
      justify-content: center;
      font-weight: 600;
      font-size: 1.25rem;
    }
    
    .client-card-body {
      padding: 1.5rem;
    }
    
    .client-name {
      font-size: 1.1rem;
      font-weight: 600;
      margin-bottom: 0.5rem;
    }
    
    .client-email, .client-phone {
      color: var(--text-light);
      font-size: 0.9rem;
      margin-bottom: 0.25rem;
    }
    
    .client-card-footer {
      padding: 1rem 1.5rem;
      border-top: 1px solid var(--border-color);
      display: flex;
      justify-content: flex-end;
      gap: 0.5rem;
    }
    
    .status-badge {
      padding: 0.25rem 0.75rem;
      border-radius: 1rem;
      font-size: 0.75rem;
      font-weight: 500;
      display: inline-block;
    }
    
    .status-badge.active {
      background-color: rgba(76, 175, 80, 0.1);
      color: var(--success-color);
      border: 1px solid rgba(76, 175, 80, 0.2);
    }
  `,
  ],
})
export class ClientCardComponent {
  @Input() client!: User
  @Output() view = new EventEmitter<User>()
  @Output() edit = new EventEmitter<User>()
  @Output() delete = new EventEmitter<number>()

  onView(): void {
    this.view.emit(this.client)
  }

  onEdit(): void {
    this.edit.emit(this.client)
  }

  onDelete(): void {
    this.delete.emit(this.client.userId)
  }
}

