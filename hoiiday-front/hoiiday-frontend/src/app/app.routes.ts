// src/app/app.routes.ts
import { Routes } from '@angular/router';
import { ClientComponent }          from './components/client/client/client.component';
import { LoginComponent }           from './components/login/login.component';
import { UserComponent }            from './components/user/user/user.component';
import { AdminComponent }           from './components/admin/admin/admin.component';
import { PropertyDetailsComponent } from './components/property-details/property-details.component';
import { BookingComponent }         from './components/booking/booking.component';

export const routes: Routes = [
  { path: '',               component: ClientComponent,           pathMatch: 'full' },
  { path: 'login',          component: LoginComponent },
  { path: 'users',          component: UserComponent },
  { path: 'admin',          component: AdminComponent },
  { path: 'properties/:id', component: PropertyDetailsComponent },
  { path: 'booking/:id',       component: BookingComponent },
  { path: '**',             redirectTo: '',                      pathMatch: 'full' },
];
