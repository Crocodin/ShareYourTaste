import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { firstValueFrom } from 'rxjs';
import { environment } from '../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class SearchService {
  private http = inject(HttpClient);

  async searchSongs(name: string, page = 0, size = 8): Promise<any> {
    const params = new HttpParams()
      .set('name', name)
      .set('page', page)
      .set('size', size);

    return firstValueFrom(
      this.http.get(`${environment.apiUrl}/api/songs/search`, { params })
    );
  }
}