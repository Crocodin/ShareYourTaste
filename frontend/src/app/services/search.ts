import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { firstValueFrom } from 'rxjs';
import { environment } from '../environments/environment';
import { Auth } from './auth';

@Injectable({
  providedIn: 'root',
})
export class SearchService {
  private http = inject(HttpClient);
  private auth = inject(Auth);

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
}