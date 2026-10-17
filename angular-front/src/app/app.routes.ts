import { Routes } from '@angular/router';
import { Accounts } from './accounts/accounts';

export const routes: Routes = [
  { path: '', redirectTo: 'accounts', pathMatch: 'full' }, // Redirection par défaut
  { path: 'accounts', component: Accounts }
];
