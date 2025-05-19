import { Injectable, Renderer2, RendererFactory2, PLATFORM_ID, Inject } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { isPlatformBrowser } from '@angular/common';

@Injectable({
  providedIn: 'root'
})
export class DarkModeService {
  private darkModeSubject = new BehaviorSubject<boolean>(false); // Default value
  darkMode$ = this.darkModeSubject.asObservable();
  private renderer: Renderer2;
  private darkModeKey = 'darkMode';
  private isBrowser: boolean;

  constructor(
    rendererFactory: RendererFactory2,
    @Inject(PLATFORM_ID) platformId: Object
  ) {
    this.renderer = rendererFactory.createRenderer(null, null);
    this.isBrowser = isPlatformBrowser(platformId);
    
    // Initialize the dark mode state
    const initialState = this.getInitialDarkModeState();
    this.darkModeSubject.next(initialState);
    
    // Initialize theme based on stored preference
    if (initialState) {
      this.enableDarkMode(false); // Don't save when initializing
    } else {
      this.disableDarkMode(false); // Don't save when initializing
    }
  }

  private getInitialDarkModeState(): boolean {
    if (!this.isBrowser) {
      return false; // Default value for server-side rendering
    }
    
    try {
      // Use sessionStorage instead of localStorage
      const storedPreference = sessionStorage.getItem(this.darkModeKey);
      if (storedPreference !== null) {
        return storedPreference === 'true';
      }
    } catch (error) {
      console.warn('SessionStorage is not available:', error);
      // Continue with fallback when sessionStorage fails
    }
    
    try {
      // Check system preference as fallback
      return window.matchMedia && window.matchMedia('(prefers-color-scheme: dark)').matches;
    } catch (error) {
      console.warn('Window matchMedia is not available:', error);
      return false; // Safe default
    }
  }

  toggleDarkMode(): void {
    if (this.isCurrentlyDarkMode) {
      this.disableDarkMode();
    } else {
      this.enableDarkMode();
    }
  }

  enableDarkMode(savePreference: boolean = true): void {
    if (this.isBrowser) {
      try {
        this.renderer.addClass(document.body, 'dark-mode');
      } catch (error) {
        console.warn('Could not add dark-mode class to body:', error);
      }
      
      if (savePreference) {
        this.setDarkModePreference(true);
      }
    }
    
    this.darkModeSubject.next(true);
  }

  disableDarkMode(savePreference: boolean = true): void {
    if (this.isBrowser) {
      try {
        this.renderer.removeClass(document.body, 'dark-mode');
      } catch (error) {
        console.warn('Could not remove dark-mode class from body:', error);
      }
      
      if (savePreference) {
        this.setDarkModePreference(false);
      }
    }
    
    this.darkModeSubject.next(false);
  }

  private setDarkModePreference(isDarkMode: boolean): void {
    if (!this.isBrowser) return;
    
    try {
      // Use sessionStorage instead of localStorage
      sessionStorage.setItem(this.darkModeKey, isDarkMode.toString());
    } catch (error) {
      console.warn('Failed to save dark mode preference to sessionStorage:', error);
      // If sessionStorage fails, we just continue without persisting the preference
    }
  }

  get isCurrentlyDarkMode(): boolean {
    return this.darkModeSubject.value;
  }
}