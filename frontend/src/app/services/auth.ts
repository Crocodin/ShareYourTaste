import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { firstValueFrom } from 'rxjs';
import { environment } from '../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class Auth {
  private isAuthenticated: boolean = false;
  private http = inject(HttpClient);

  async login(username: string, password: string): Promise<any> {
    const response = await firstValueFrom(this.http.post(`${environment.apiUrl}/api/login`, { username, password }));
    this.isAuthenticated = true;
    return response;
  }

  logout(): void {
    this.isAuthenticated = false;
  }

  isLoggedIn(): boolean {
    return this.isAuthenticated;
  }
}
