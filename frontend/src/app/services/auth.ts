import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { firstValueFrom } from 'rxjs';
import { environment } from '../environments/environment';
import { Router } from '@angular/router';
import { User } from '../model/user.model';

@Injectable({
  providedIn: 'root',
})
// auth is basically just the user service, but it also keeps track of the current user and whether they are logged in or not
export class Auth {
  private isAuthenticated: boolean = false;
  private http = inject(HttpClient);
  private currentUser: User | null = null;
  private router = inject(Router);

  async login(username: string, password: string): Promise<User> {
    const response = await firstValueFrom(this.http.post(`${environment.apiUrl}/api/users/login`, { username, password }, { withCredentials: true }));
    this.isAuthenticated = true;
    this.currentUser = response as User;
    return response as User;
  }

  async findUserById(userId: number): Promise<User> {
    const response = await firstValueFrom(this.http.get(`${environment.apiUrl}/api/users/${userId}`, { withCredentials: true }));
    return response as User;
  }

  async isFollowing(profileId: number): Promise<boolean> {
    return await firstValueFrom(this.http.get(`${environment.apiUrl}/api/users/${profileId}/is-following`, { withCredentials: true })) as boolean;
  }

  async followUser(profileId: number): Promise<void> {
    await firstValueFrom(this.http.post(`${environment.apiUrl}/api/users/${profileId}/follow`, {}, { withCredentials: true }));
  }

  async unfollowUser(profileId: number): Promise<void> {
    await firstValueFrom(this.http.delete(`${environment.apiUrl}/api/users/${profileId}/unfollow`, { withCredentials: true }));
  }

  logout(): void {
    this.isAuthenticated = false;
    this.currentUser = null;
  }

  isLoggedIn(): boolean {
    return this.isAuthenticated;
  }

  getCurrentUser(): User | null {
    return this.currentUser;
  }

  getUserId(): number {
    console.log(this.currentUser);
    return this.currentUser?.userId || -1;
  }

  navigateToUserPage() {
    if (this.isLoggedIn()) {
      this.router.navigate(['/profile/' + this.getUserId()]);
    } else {
      this.router.navigate(['/login']);
    }
  }

  async saveProfileAvatar(file: File): Promise<string> {
    const formData = new FormData();
    formData.append('file', file);

    const result: any = await firstValueFrom(
      this.http.post(`${environment.apiUrl}/api/users/update-avatar`, formData, { withCredentials: true })
    );
    
    return result.path;
  }

  async updateProfile(profileData: any): Promise<User> {
    const updated: any = await firstValueFrom(
      this.http.put(`${environment.apiUrl}/api/users/${this.getUserId()}/update`, profileData, { withCredentials: true })
    );
    this.currentUser = { ...this.currentUser, ...updated };
    return this.currentUser as User;
  }

  setUser(user: User) {
    this.currentUser = user;
  }

  getImageUrl(path: string | null): string {
    if (!path) return '';
    if (path.startsWith('http')) return path;
    console.log(`${environment.apiUrl}${path}`)
    return `${environment.apiUrl}${path}`; // e.g. http://localhost:8080/avatars/1_photo.jpg
  }

  async register(username: string, password: string, email: string): Promise<any> {
    const response = await firstValueFrom(
      this.http.post(`${environment.apiUrl}/api/users/register`, { username, password, email })
    );
    return response;
  }
}
