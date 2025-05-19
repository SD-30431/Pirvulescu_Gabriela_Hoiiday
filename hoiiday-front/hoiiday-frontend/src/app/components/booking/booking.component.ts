import {
  Component,
  OnInit,
  OnDestroy,
} from '@angular/core';
import {
  ActivatedRoute,
  Router,
  RouterModule,
} from '@angular/router';

import { AuthService } from '../../services/auth.service';
import { PropertyService } from '../../services/property.service';
import {
  catchError,
  of,
  take,
  Subscription,
  switchMap,
  tap,
  finalize,
} from 'rxjs';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Property } from '../../model/property/property.model';
import { formatDate } from '@angular/common';
import { BookingService } from '../../services/booking.service';
import { BookingRequest } from '../../model/booking.model';
import * as xml2js from 'xml2js';

@Component({
  selector: 'app-booking',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './booking.component.html',
  styleUrls: ['./booking.component.css'],
})
export class BookingComponent implements OnInit, OnDestroy {
  propertyId!: number;
  property: Property | null = null;

  // Booking details
  checkInDate: string = '';
  checkOutDate: string = '';
  selectedRoomType: string = '';
  roomsCount: number = 1;

  // Booking summary
  nightsCount: number = 0;
  roomPrice: number = 0;
  totalPrice: number = 0;

  // UI states
  loading: boolean = true;
  errorMessage: string = '';
  processingBooking: boolean = false;
  minDate: string = '';
  successMessage: string = '';

  private authSubscription: Subscription | null = null;
  private propertySubscription: Subscription | null = null;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private bookingService: BookingService,
    private authService: AuthService,
    private propertyService: PropertyService
  ) {
    this.minDate = formatDate(new Date(), 'yyyy-MM-dd', 'en');
  }

  ngOnInit() {
    this.propertyId = +this.route.snapshot.paramMap.get('id')!;
    this.checkAuthAndLoadProperty();
  }

  ngOnDestroy(): void {
    if (this.authSubscription) {
      this.authSubscription.unsubscribe();
    }
    if (this.propertySubscription) {
      this.propertySubscription.unsubscribe();
    }
  }

  checkAuthAndLoadProperty() {
    this.authService
      .isAuthenticated()
      .pipe(
        tap((isAuthenticated) => {
          if (!isAuthenticated) {
            this.router.navigate(['/login'], {
              queryParams: {
                returnUrl: `/booking/${this.propertyId}`,
              },
            });
          }
        }),
        switchMap((isAuthenticated) => {
          if (isAuthenticated) {
            return this.loadPropertyDetails();
          } else {
            return of(null);
          }
        })
      )
      .subscribe();
  }

  loadPropertyDetails() {
    this.loading = true;
    return this.propertyService
      .getPropertyById(this.propertyId)
      .pipe(
        tap((property) => {
          this.property = property;
          if (property.propertyRooms.length > 0) {
            this.selectedRoomType = property.propertyRooms[0].roomType;
            this.updateRoomPrice();
          }
          this.loading = false;
        }),
        catchError((error) => {
          this.errorMessage =
            'Failed to load property details. Please try again.';
          this.loading = false;
          return of(null);
        })
      );
  }

  get isLoggedIn() {
    return this.authService.isAuthenticated();
  }

  get availableRoomTypes() {
    return (
      this.property?.propertyRooms.filter(
        (room) => room.qtyTotal > 0
      ) || []
    );
  }

  get selectedRoom() {
    return this.property?.propertyRooms.find(
      (room) => room.roomType === this.selectedRoomType
    );
  }

  get maxRoomsAvailable(): number {
    return this.selectedRoom?.qtyTotal || 0;
  }

  onRoomTypeChange() {
    this.updateRoomPrice();
    if (this.roomsCount > this.maxRoomsAvailable) {
      this.roomsCount = this.maxRoomsAvailable;
    }
    this.calculateTotal();
  }

  updateRoomPrice() {
    this.roomPrice = this.selectedRoom?.price || 0;
    this.calculateTotal();
  }

  onDateChange() {
    this.calculateNights();
    this.calculateTotal();
  }

  onRoomsCountChange() {
    this.calculateTotal();
  }

  calculateNights() {
    if (this.checkInDate && this.checkOutDate) {
      const checkIn = new Date(this.checkInDate);
      const checkOut = new Date(this.checkOutDate);
      const timeDiff = checkOut.getTime() - checkIn.getTime();
      this.nightsCount = Math.ceil(timeDiff / (1000 * 3600 * 24));
      if (this.nightsCount <= 0) {
        this.nightsCount = 0;
        this.errorMessage =
          'Check-out date must be after check-in date';
      } else {
        this.errorMessage = '';
      }
    } else {
      this.nightsCount = 0;
    }
  }

  calculateTotal() {
    if (this.nightsCount > 0 && this.roomPrice > 0) {
      this.totalPrice =
        this.nightsCount * this.roomPrice * this.roomsCount;
    } else {
      this.totalPrice = 0;
    }
  }

  generateXmlBookingDetails() {
    this.errorMessage = '';

    // Basic client‐side validation
    if (!this.checkInDate || !this.checkOutDate) {
      this.errorMessage = 'Please pick both check-in and check-out dates';
      return;
    }

    // Build the request payload however your backend expects
    const bookingRequest = {
      userId: 2,                             // replace with real userId
      roomType: this.selectedRoomType,
      rooms: this.roomsCount,
      from: this.checkInDate,
      to: this.checkOutDate
    };

    this.processingBooking = true;
    this.bookingService.bookAndGetXml(this.propertyId, bookingRequest)
      .pipe(finalize(() => this.processingBooking = false))
      .subscribe({
        next: xmlString => this.downloadXmlFile(xmlString),
        error: err => this.errorMessage = err.message || err
      });
  }

  private downloadXmlFile(xml: string) {
    // Create a blob out of the XML string
    const blob = new Blob([xml], { type: 'application/xml' });
    const url  = window.URL.createObjectURL(blob);

    // Create a temporary <a> and click it
    const a = document.createElement('a');
    a.href = url;
    a.download = `booking_${this.propertyId}.xml`;
    document.body.appendChild(a);
    a.click();

    // Cleanup
    document.body.removeChild(a);
    window.URL.revokeObjectURL(url);
  }



  submitBooking() {
    if (!this.validateBooking()) {
      return;
    }

    // Use a default user ID if needed
    const userId = this.authService.getSnapshotCurrentUser()?.userId || 1;

    this.processingBooking = true;
    this.errorMessage = '';

    const bookingRequest: BookingRequest = {
      userId: userId,
      roomType: this.selectedRoomType as any,
      rooms: this.roomsCount,
      from: this.checkInDate,
      to: this.checkOutDate,
    };

    console.log('Submitting booking with request:', bookingRequest);

    this.bookingService.bookAndGetXml(this.propertyId, bookingRequest)
      .subscribe({
        next: (xmlData) => {
          this.processingBooking = false;

          if (!xmlData) {
            this.errorMessage = 'No data received from server.';
            return;
          }

          console.log('Booking XML Data:', xmlData);

          try {
            // Extract data from parsed XML
            const parser = new xml2js.Parser();
            let bookingDetails: any;
            parser.parseString(xmlData, (err, result) => {
              if (err) {
                throw new Error('Failed to parse XML data');
              }
              bookingDetails = result.bookingDetails;
            });
            const propertyName = bookingDetails.property?.name;
            const totalPrice = bookingDetails.booking?.totalPrice;
            const roomType = bookingDetails.booking?.roomSelection?.roomType;
            const nights = bookingDetails.booking?.dates?.lengthOfStay;

            this.router.navigate(['/booking-confirmation'], {
              state: {
                booking: xmlData,
                propertyName: propertyName,
                roomType: roomType || this.selectedRoomType,
                nights: nights || this.nightsCount,
                totalPrice: totalPrice || this.totalPrice,
              },
            });
          } catch (error) {
            console.error('Error parsing XML data:', error);
            this.errorMessage = 'Failed to process booking details. Please try again.';
          }
        },
        error: (error) => {
          this.processingBooking = false;
          console.error('Booking error:', error);
          this.errorMessage = error.message || 'Failed to complete booking. Please try again.';
        }
      });
  }

  validateBooking(): boolean {
    this.errorMessage = '';

    if (!this.checkInDate || !this.checkOutDate) {
      this.errorMessage = 'Please select check-in and check-out dates';
      return false;
    }

    if (this.nightsCount <= 0) {
      this.errorMessage = 'Check-out date must be after check-in date';
      return false;
    }

    if (!this.selectedRoomType) {
      this.errorMessage = 'Please select a room type';
      return false;
    }

    if (this.roomsCount <= 0) {
      this.errorMessage = 'Please select at least one room';
      return false;
    }

    if (this.roomsCount > this.maxRoomsAvailable) {
      this.errorMessage = `Only ${this.maxRoomsAvailable} rooms available for this type`;
      return false;
    }

    return true;
  }

  cancel() {
    this.router.navigate(['/properties', this.propertyId]);
  }

  formatRoomType(roomType: string): string {
    return (
      roomType.charAt(0).toUpperCase() + roomType.slice(1).toLowerCase()
    );
  }
}