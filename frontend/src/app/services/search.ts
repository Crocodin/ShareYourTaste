import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { firstValueFrom } from 'rxjs';
import { environment } from '../environments/environment';
import { Page } from '../model/page.model';
import { Song } from '../model/song.model';
import { Comment } from '../model/comment.model';

@Injectable({
  providedIn: 'root',
})
export class SearchService {
  private http = inject(HttpClient);

  async searchSongs(name: string, page = 0, size = 5, sort = 'songRating,desc'): Promise<Page<Song>> {
    const params = new HttpParams()
      .set('name', name)
      .set('page', page)
      .set('size', size)
      .set('sort', sort);

    return firstValueFrom(
      this.http.get<Page<Song>>(`${environment.apiUrl}/api/songs/search`, { params, withCredentials: true })
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

  async getSongById(songId: number): Promise<Song> {
    return firstValueFrom(
      this.http.get<Song>(`${environment.apiUrl}/api/songs/${songId}`, { withCredentials: true })
    );
  }

  async getComments(songId: number, page: number = 0, size: number = 5, sort: string = 'commentId,desc'): Promise<Page<Comment>> {
    const params = new HttpParams()
      .set('page', page)
      .set('size', size)
      .set('sort', sort);

    return firstValueFrom(
      this.http.get<Page<Comment>>(`${environment.apiUrl}/api/songs/${songId}/comments`, { params, withCredentials: true })
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

  async findUserActivities(userId: number): Promise<Song[]> {
    return firstValueFrom(
      this.http.get<Song[]>(`${environment.apiUrl}/api/songs/${userId}/activities`, { withCredentials: true })
    );
  }

  async findUserComments(userId: number): Promise<Comment[]> {
    return firstValueFrom(
      this.http.get<Comment[]>(`${environment.apiUrl}/api/users/${userId}/comments`, { withCredentials: true })
    );
  }
}