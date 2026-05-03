import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { firstValueFrom } from 'rxjs';
import { environment } from '../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class SearchService {
  private http = inject(HttpClient);

  async searchSongs(name: string, page = 0, size = 5, sort = 'songRating,desc'): Promise<any> {
    const params = new HttpParams()
      .set('name', name)
      .set('page', page)
      .set('size', size)
      .set('sort', sort);

    return firstValueFrom(
      this.http.get(`${environment.apiUrl}/api/songs/search`, { params, withCredentials: true })
    );
  }

  async rateSong(songId: number, rating: number): Promise<any> {
    return firstValueFrom(
      this.http.post(
        `${environment.apiUrl}/api/songs/${songId}/rate`,
        { rating },
        { withCredentials: true }
      )
    );
  }

  async getSongById(songId: number): Promise<any> {
    return firstValueFrom(
      this.http.get(`${environment.apiUrl}/api/songs/${songId}`, { withCredentials: true })
    );
  }

  async getComments(songId: number, page: number = 0, size: number = 5, sort: string = 'commentId,desc'): Promise<any> {
    const params = new HttpParams()
      .set('page', page)
      .set('size', size)
      .set('sort', sort);

    return firstValueFrom(
      this.http.get(`${environment.apiUrl}/api/songs/${songId}/comments`, { params, withCredentials: true })
    );
  }
  
  async addComment(songId: number, comment: string): Promise<any> {
    return firstValueFrom(
      this.http.post(
        `${environment.apiUrl}/api/songs/${songId}/comment`,
        { content: comment },
        { withCredentials: true }
      )
    );
  }
}