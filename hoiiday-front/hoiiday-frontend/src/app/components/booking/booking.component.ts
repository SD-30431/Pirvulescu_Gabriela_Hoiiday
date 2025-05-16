import { Component, OnInit }     from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { BookingService, BookingRequest } from '../../services/booking.service';
import { AuthService }           from '../../services/auth.service';
import { PropertyService }       from '../../services/property.service';
import { catchError, of }        from 'rxjs';
import { CommonModule }          from '@angular/common';
import { FormsModule }           from '@angular/forms';
import { Property }              from '../../model/property/property.model';

@Component({
  selector: 'app-booking',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './booking.component.html',
  styleUrls: ['./booking.component.css'],
})
export class BookingComponent implements OnInit {
  propertyId!: number;
  property: Property | null = null;

  checkInDate  = '';
  checkOutDate = '';
  rooms        = 1;
  selectedType = '';
  errorMessage = '';

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private bookingSvc: BookingService,
    private auth: AuthService,
    private propSvc: PropertyService
  ) {}

  ngOnInit() {
    this.propertyId = +this.route.snapshot.paramMap.get('id')!;
    // load the full property (including real room-types & counts)
    this.propSvc.getPropertyById(this.propertyId).subscribe(p => {
      this.property = p;
      if (p.propertyRooms.length) {
        this.selectedType = p.propertyRooms[0].roomType;
      }
    });
  }

  /** True only if the user is logged in */
  get isLoggedIn(): boolean {
    return this.auth.isAuthenticated();
  }

  submitBooking() {
    this.errorMessage = '';

    const userId = this.auth.getCurrentUserId();
    if (!userId) {
      this.errorMessage = 'You must be logged in to book';
      return;
    }

    if (!this.property) {
      this.errorMessage = 'Property not loaded';
      return;
    }

    const req: BookingRequest = {
      userId,
      roomType: this.selectedType as any,
      rooms:    this.rooms,
      from:     this.checkInDate,
      to:       this.checkOutDate
    };

    this.bookingSvc.book(this.propertyId, req)
      .pipe(
        catchError(err => {
          this.errorMessage = err.error?.error || 'Unexpected error';
          return of(null);
        })
      )
      .subscribe(resp => {
        if (!resp) return;

        alert(`Booked! Confirmation #${resp.bookingId}`);

        // decrement the local count so the dropdown updates
        const slot = this.property!.propertyRooms
          .find(r => r.roomType === this.selectedType);
        if (slot) {
          slot.qtyTotal = slot.qtyTotal - this.rooms;
          // reset rooms count if we overshot
          if (this.rooms > slot.qtyTotal) {
            this.rooms = slot.qtyTotal;
          }
        }

        this.router.navigate(['/client']);
      });
  }

  cancel() {
    this.router.navigate(['/client']);
  }
}
