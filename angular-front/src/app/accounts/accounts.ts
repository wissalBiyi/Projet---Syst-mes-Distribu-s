import { Component, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CommonModule, AsyncPipe } from '@angular/common';
import { Observable, catchError, map, of } from 'rxjs';

export enum RequestStatus {
  LOADING = 'LOADING',
  SUCCESS = 'SUCCESS',
  ERROR = 'ERROR'
}

export interface AccountListState {
  status: RequestStatus;
  accounts?: any[];
  errorMessage?: string;
}

@Component({
  selector: 'app-accounts',
  standalone: true,
  imports: [CommonModule, AsyncPipe],
  templateUrl: './accounts.html',
  styleUrl: './accounts.css',
})
export class Accounts {
  private http = inject(HttpClient);

  // Exposer l'enum pour qu'elle soit accessible dans le fichier HTML
  protected readonly RequestStatus = RequestStatus;

  accounts$: Observable<AccountListState> = this.http.get<any[]>("http://localhost:9999/EBANK-SERVICE/accounts")
    .pipe(
      map(resp => ({
        status: RequestStatus.SUCCESS,
        accounts: resp
      })),
      catchError((err) =>
        of({
          status: RequestStatus.ERROR,
          errorMessage: err.statusText || 'Internal Server Error'
        })
      )
    );
}
