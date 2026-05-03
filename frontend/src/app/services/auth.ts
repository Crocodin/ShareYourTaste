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
  private currentUser: any = null;

  async login(username: string, password: string): Promise<any> {
    const response = await firstValueFrom(this.http.post(`${environment.apiUrl}/api/login`, { username, password }, { withCredentials: true }));
    this.isAuthenticated = true;
    this.currentUser = response;
    return response;
  }

  logout(): void {
    this.isAuthenticated = false;
    this.currentUser = null;
  }

  isLoggedIn(): boolean {
    return this.isAuthenticated;
  }

  getCurrentUser(): any {
    return this.currentUser;
  }

  getUserId(): number {
    console.log(this.currentUser);
    return this.currentUser?.userId || -1;
  }
}
