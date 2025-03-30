import { Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { AdminComponent } from './components/admin/admin/admin.component';
import { UserComponent } from './components/user/user/user.component';
import { ClientComponent } from './components/client/client/client.component';

export const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'users', component: UserComponent }, 
  { path: 'admin', component: AdminComponent },
  { path: 'client', component: ClientComponent },
  { path: '', redirectTo: '/login', pathMatch: 'full' },
];