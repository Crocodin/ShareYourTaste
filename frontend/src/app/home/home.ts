import { ChangeDetectorRef, Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { SearchService } from '../services/search';

@Component({
  selector: 'app-home',
  imports: [FormsModule],
  templateUrl: './home.html',
  styleUrl: './home.css',
})
export class Home {
  searchQuery: string = '';
  searchService: SearchService = inject(SearchService);
  songs: any[] = [];
  cdr: ChangeDetectorRef = inject(ChangeDetectorRef);

  onSearchInput() {
    this.searchService.searchSongs(this.searchQuery).then((response) => {
      this.songs = response.content;
      this.cdr.detectChanges();
    });
  }
}
