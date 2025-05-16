import { Component, type OnInit } from "@angular/core"
import { CommonModule } from "@angular/common"
import { FormsModule } from "@angular/forms"
import type { Property } from "../../../model/property/property.model"
import { PropertyService } from "../../../services/property.service"
import { Router } from "@angular/router"
import { LoginComponent } from "../../login/login.component";

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
    private router: Router,
  ) {}

  ngOnInit(): void {
    this.loadProperties()
    this.checkLoginStatus()
  }

  checkLoginStatus(): void {
    this.isLoggedIn = !!localStorage.getItem("authToken")
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
    this.loading = true
    console.log("Searching properties with:", {
      location: this.location,
      checkInDate: this.checkInDate,
      checkOutDate: this.checkOutDate,
      guestCount: this.guestCount,
      filters: this.filters,
    })

    // In a real application, you would send these filters to your backend
    setTimeout(() => {
      this.filteredProperties = this.properties.filter((property) => {
        // Filter by location if provided
        if (
          this.location &&
          !property.location.city.toLowerCase().includes(this.location.toLowerCase()) &&
          !property.location.country.toLowerCase().includes(this.location.toLowerCase())
        ) {
          return false
        }

        // Filter by guest count
        if (this.guestCount > property.maxGuests) {
          return false
        }

        return true
      })

      this.applyFilters()
      this.applySorting()
      this.loading = false
    }, 1000)
  }

  applyFilters(): void {
    // Apply amenity filters, rating filters, etc.
    this.filteredProperties = this.properties.filter((property) => {
      // Filter by location if provided
      if (
        this.location &&
        !property.location.city.toLowerCase().includes(this.location.toLowerCase()) &&
        !property.location.country.toLowerCase().includes(this.location.toLowerCase())
      ) {
        return false
      }

      // Filter by guest count
      if (this.filters.guestCount > property.maxGuests) {
        return false
      }

      // In a real app, you would check if the property has the selected amenities
      // For now, we'll just simulate this
      if (this.filters.amenities.pool && !this.hasAmenity(property, "pool")) {
        return false
      }
      if (this.filters.amenities.wifi && !this.hasAmenity(property, "wifi")) {
        return false
      }
      if (this.filters.amenities.petFriendly && !this.hasAmenity(property, "petFriendly")) {
        return false
      }
      if (this.filters.amenities.seaView && !this.hasAmenity(property, "seaView")) {
        return false
      }
      if (this.filters.amenities.parking && !this.hasAmenity(property, "parking")) {
        return false
      }

      // Filter by rating
      if (this.filters.rating > 0 && this.getPropertyRating(property) < this.filters.rating) {
        return false
      }

      return true
    })

    this.applySorting()
  }

  // Helper method to check if a property has a specific amenity
  private hasAmenity(property: Property, amenity: string): boolean {
    // In a real app, you would check the property's amenities
    // For now, we'll return a random boolean
    return Math.random() > 0.5
  }

  // Helper method to get a property's rating
  private getPropertyRating(property: Property): number {
    // In a real app, you would get the actual rating
    // For now, we'll return a random rating between 7 and 10
    return 7 + Math.floor(Math.random() * 4)
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

  // Helper method to get the lowest price from a property
  private getLowestPrice(property: Property): number {
    // In a real app, you would get the actual price from the property
    return property.propertyId * 100 // Just a placeholder
  }

  onSortChange(): void {
    this.applySorting()
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
    this.selectedPropertyForBooking = null
  }

  onLoginSuccess(): void {
    this.showLoginModal = false
    this.isLoggedIn = true
    if (this.selectedPropertyForBooking) {
      this.router.navigate(["/book", this.selectedPropertyForBooking.propertyId])
      this.selectedPropertyForBooking = null
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
