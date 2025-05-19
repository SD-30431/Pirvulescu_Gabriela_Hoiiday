import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { PropertyService } from '../../services/property.service';
import { Property } from '../../model/property/property.model';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import jsPDF from 'jspdf';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-property-details',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './property-details.component.html',
  styleUrls: ['./property-details.component.css'],
})
export class PropertyDetailsComponent implements OnInit {
  property: Property | null = null;
  loading = true;
  errorMessage = '';
  
  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private propertyService: PropertyService,
    private authService: AuthService
  ) {}
  
  ngOnInit() {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.loadPropertyDetails(id);
  }
  
  loadPropertyDetails(id: number) {
    this.propertyService.getPropertyById(id).subscribe({
      next: data => {
        this.property = data;
        this.loading = false;
      },
      error: err => {
        this.errorMessage = 'Failed to load property details. Please try again.';
        this.loading = false;
      }
    });
  }
  
  generatePdf() {
    if (!this.property) return;
    
    const doc = new jsPDF({ unit: 'pt', format: 'a4' });
    let y = 40;
    const margin = 40;
    
    // Title + meta
    doc.setFontSize(18);
    doc.text(this.property.name, margin, y);
    y += 30;
    
    doc.setFontSize(12);
    doc.text(
      `Location: ${this.property.location.city}, ${this.property.location.country}`,
      margin,
      y
    );
    y += 20;
    doc.text(`Max Guests: ${this.property.maxGuests}`, margin, y);
    y += 20;
    doc.text(
      `Room types: ${this.property.propertyRooms.length}`,
      margin,
      y
    );
    y += 30;
    
    // Table header
    doc.setFontSize(14);
    doc.text('Type', margin, y);
    doc.text('Qty', margin + 200, y);
    doc.text('Price (per night)', margin + 300, y);
    y += 15;
    doc.line(margin, y, 550, y);
    y += 15;
    doc.setFontSize(12);
    
    // Loop REAL rooms
    for (const room of this.property.propertyRooms) {
      if (y > 750) {
        doc.addPage();
        y = 40;
      }
      doc.text(this.humanize(room.roomType), margin, y);
      doc.text(String(room.qtyTotal), margin + 200, y);
      doc.text(`$${room.price}`, margin + 300, y);
      y += 20;
    }
    
    doc.save(`${this.property.name.replace(/\s+/g,'_')}_rooms.pdf`);
  }
  
  private humanize(rt: string) {
    // turn "SINGLE" into "Single Room"
    const lower = rt.toLowerCase();
    return `${lower.charAt(0).toUpperCase() + lower.slice(1)} Room`;
  }
  
  goBack() {
    this.router.navigateByUrl('/');
  }
  
  goToBooking() {
    // Check if user is authenticated before proceeding to booking
    if (!this.authService.isAuthenticated()) {
      // Store intended destination
      localStorage.setItem('redirectAfterLogin', `/booking/${this.property?.propertyId}`);
      // Redirect to login with return URL
      this.router.navigate(['/login'], { 
        queryParams: { returnUrl: `/booking/${this.property?.propertyId}` } 
      });
      return;
    }
    
    if (this.property) {
      this.router.navigate(['/booking', this.property.propertyId]);
    }
  }
}