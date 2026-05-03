import { ChangeDetectorRef, Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { SearchService } from '../services/search';
import { DecimalPipe } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { Auth } from '../services/auth';

@Component({
  selector: 'app-home',
  imports: [FormsModule, DecimalPipe],
  templateUrl: './home.html',
  styleUrl: './home.css',
})
export class Home {
  searchQuery: string = '';
  searchService: SearchService = inject(SearchService);
  cdr: ChangeDetectorRef = inject(ChangeDetectorRef);
  router: Router = inject(Router);
  route = inject(ActivatedRoute);
  songs: any[] = [];
  auth = inject(Auth)

  rowHeight: number = 100; // approximate height of one song card
  pageSize: number = 5;

  currentPage: number = 0;
  totalPages: number = 0;

  onSearchInput(page: number = 0) {
    this.searchService.searchSongs(this.searchQuery, page, this.pageSize).then((response) => {
      this.songs = response.content;
      this.currentPage = response.number;
      this.totalPages = response.totalPages;
      this.cdr.detectChanges();
    });
  }

  goToPage(page: number) {
    if (page >= 0 && page < this.totalPages) {
      this.onSearchInput(page);
    }
  }

  // Helper to create an array for pagination buttons
  totalPagesArray(): number[] {
    return Array.from({ length: this.totalPages }, (_, i) => i);
  }

  rateSong(song: any, rating: number) {
    console.log(`Rating song ${song.songId} with ${rating} stars`);
    this.searchService.rateSong(song.songId, rating).then(() => {
      song.userRating = rating;
    });
  }

  private calculatePageSize() {
    const headerHeight = 80;
    const paginationHeight = 80; 
    const padding = 40;         

    const availableHeight = window.innerHeight - headerHeight - paginationHeight - padding;

    const size = Math.floor(availableHeight / this.rowHeight);

    this.pageSize = Math.max(3, size);
  }

  ngOnInit() {
    this.calculatePageSize();
    window.addEventListener('resize', () => {
      this.calculatePageSize();
    });

    this.route.queryParamMap.subscribe((params) => {
      this.searchQuery = params.get('q') || '';
      if (this.searchQuery.trim()) {
        this.onSearchInput();
      }
    });
  }

  goToSong(id: number) {
    this.router.navigate(['/songs', id]);
  }

  onUserClick() {
    // if the user is login, show profile or logout option
    // else, navigate to login page
    if (this.auth.isLoggedIn()) {
      // For simplicity, we'll just log out the user on click
      console.log('Logging in user');
    } else {
      this.router.navigate(['/login']);
    }
  }
}
