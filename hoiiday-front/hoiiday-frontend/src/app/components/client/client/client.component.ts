import { Component, Inject, PLATFORM_ID, type OnInit } from "@angular/core"
import { CommonModule, isPlatformBrowser } from "@angular/common"
import { FormsModule } from "@angular/forms"
import type { Property } from "../../../model/property/property.model"
import { PropertyService } from "../../../services/property.service"
import { Router } from "@angular/router"
import { LoginComponent } from "../../login/login.component";
import { AuthService, LoginSuccessPayload } from "../../../services/auth.service"
import { take } from "rxjs"

@Component({
  selector: "app-client",
  standalone: true,
  imports: [CommonModule, FormsModule, LoginComponent],
  templateUrl: "./client.component.html",
  styleUrls: ["./client.component.css"],
})
export class ClientComponent implements OnInit {
  properties: Property[] = []
  filteredProperties: Property[] = []
  selectedPropertyForBooking: Property | null = null
  errorMessage = ""
  loading = true
  showLoginModal = false
  isLoggedIn = false
  showFilters = true

  // Search form properties
  location = ""
  checkInDate = ""
  checkOutDate = ""
  guestCount = 1

  // Filter properties
  filters = {
    amenities: {
      pool: false,
      wifi: false,
      petFriendly: false,
      seaView: false,
      parking: false,
    },
    guestCount: 1,
    rating: 0,
  }

  // Sorting options
  sortOptions = [
    { value: "name_asc", label: "Name (A-Z)" },
    { value: "name_desc", label: "Name (Z-A)" },
    { value: "price_asc", label: "Price (Low to High)" },
    { value: "price_desc", label: "Price (High to Low)" },
  ]
  selectedSort = "name_asc"

  constructor(
    private propertyService: PropertyService,
    private authService: AuthService,
    private router: Router,
    @Inject(PLATFORM_ID) private platformId: Object
  ) {}

  ngOnInit(): void {
    // Only in browser do we read localStorage / AuthService
    if (isPlatformBrowser(this.platformId)) {
      this.authService
        .isAuthenticated()
        .pipe(take(1))
        .subscribe((loggedIn) => {
          this.isLoggedIn = loggedIn;
          if (this.isLoggedIn) {
            this.loadProperties();
          }
        });
    }
  }


  checkLoginStatus(): void {
    if (isPlatformBrowser(this.platformId)) {
      this.isLoggedIn = !!localStorage.getItem("authToken");
    } else {
      this.isLoggedIn = false;
    }
  }

  loadProperties(): void {
    this.loading = true
    this.propertyService.getAllProperties().subscribe({
      next: (data) => {
        this.properties = data
        this.filteredProperties = [...data]
        this.applySorting()
        this.loading = false
      },
      error: (error) => {
        this.errorMessage = "Failed to load properties. Please try again later."
        this.loading = false
        console.error("Error loading properties:", error)
      },
    })
  }

  searchProperties(): void {
    this.filteredProperties = this.properties.filter((p) => {
      if (this.location) {
        const city = p.location.city.toLowerCase();
        const country = p.location.country.toLowerCase();
        if (!city.includes(this.location.toLowerCase()) && !country.includes(this.location.toLowerCase())) {
          return false;
        }
      }
      if (this.guestCount > p.maxGuests) {
        return false;
      }
      return true;
    });
    this.applyFilters();
    this.applySorting();
  }

  applyFilters(): void {
    this.filteredProperties = this.properties.filter((property) => {
      if (
        this.location &&
        !property.location.city.toLowerCase().includes(this.location.toLowerCase()) &&
        !property.location.country.toLowerCase().includes(this.location.toLowerCase())
      ) {
        return false;
      }
      if (this.filters.guestCount > property.maxGuests) {
        return false;
      }
      if (this.filters.amenities.pool && !this.hasAmenity(property, "pool")) {
        return false;
      }
      if (this.filters.amenities.wifi && !this.hasAmenity(property, "wifi")) {
        return false;
      }
      if (this.filters.amenities.petFriendly && !this.hasAmenity(property, "petFriendly")) {
        return false;
      }
      if (this.filters.amenities.seaView && !this.hasAmenity(property, "seaView")) {
        return false;
      }
      if (this.filters.amenities.parking && !this.hasAmenity(property, "parking")) {
        return false;
      }
      if (this.filters.rating > 0 && this.getPropertyRating(property) < this.filters.rating) {
        return false;
      }
      return true;
    });

    this.applySorting();
  }

  
  private hasAmenity(property: Property, amenity: string): boolean {
    return Math.random() > 0.5;
  }

  private getPropertyRating(property: Property): number {
    return 7 + Math.floor(Math.random() * 4);
  }

  applySorting(): void {
    switch (this.selectedSort) {
      case "name_asc":
        this.filteredProperties.sort((a, b) => a.name.localeCompare(b.name))
        break
      case "name_desc":
        this.filteredProperties.sort((a, b) => b.name.localeCompare(a.name))
        break
      case "price_asc":
        this.filteredProperties.sort((a, b) => {
          const aPrice = this.getLowestPrice(a)
          const bPrice = this.getLowestPrice(b)
          return aPrice - bPrice
        })
        break
      case "price_desc":
        this.filteredProperties.sort((a, b) => {
          const aPrice = this.getLowestPrice(a)
          const bPrice = this.getLowestPrice(b)
          return bPrice - aPrice
        })
        break
    }
  }

  private getLowestPrice(property: Property): number {
    return property.propertyId * 100;
  }

  onSortChange(): void {
    this.applySorting();
  }

  viewPropertyDetails(propertyId: number): void {
    console.log("Navigating to details for property ID:", propertyId); // DEBUGGING
    this.router.navigate(["/properties", propertyId]);
  }

  bookProperty(property: Property): void {
    this.selectedPropertyForBooking = property
    if (this.isLoggedIn) {
      this.router.navigate(["/book", property.propertyId])
    } else {
      this.showLoginModal = true
    }
  }

  closeLoginModal(): void {
    this.showLoginModal = false
  }

  /**
   * Called when <app-login> emits loginSuccess
   * @param payload contains .token and any user info
   */
  onLoginSuccess(payload: LoginSuccessPayload): void {
    // AuthService already stored the token
    this.showLoginModal = false;
    this.isLoggedIn = true;
    // now that we're authenticated, load properties
    this.loadProperties();

    // If the user was mid-booking, continue:
    if (this.selectedPropertyForBooking) {
      this.router.navigate(['/booking', this.selectedPropertyForBooking.propertyId]);
      this.selectedPropertyForBooking = null;
    }
  }


  resetFilters(): void {
    this.filters = {
      amenities: {
        pool: false,
        wifi: false,
        petFriendly: false,
        seaView: false,
        parking: false,
      },
      guestCount: 1,
      rating: 0,
    }
    this.applyFilters()
  }

  toggleFilters(): void {
    this.showFilters = !this.showFilters
  }
}
