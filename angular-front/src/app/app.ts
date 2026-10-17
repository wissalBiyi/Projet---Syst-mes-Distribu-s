import { Component, signal } from '@angular/core';
import { RouterOutlet, RouterLink } from '@angular/router'; // 1. Importez RouterLink ici

@Component({
  imports: [RouterOutlet, RouterLink], // 2. Ajoutez RouterLink dans le tableau des imports
  selector: 'app-root',
  styleUrl: './app.css',
  templateUrl: './app.html',
})
export class App {
  protected readonly title = signal('angular-front');
}
