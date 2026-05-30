import { ChangeDetectorRef, Component, inject } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { SearchService } from '../../services/search';
import { FormsModule } from '@angular/forms';
import { DecimalPipe } from '@angular/common';
import { Auth } from '../../services/auth';

@Component({
  selector: 'app-song-detail',
  imports: [FormsModule, DecimalPipe],
  templateUrl: './song-detail.html',
  styleUrl: './song-detail.css',
})
export class SongDetail {
  route = inject(ActivatedRoute);
  songId: number = (parseInt(this.route.snapshot.paramMap.get('id') || '0'));
  searchQuery: string = '';
  router = inject(Router);
  crd: ChangeDetectorRef = inject(ChangeDetectorRef);
  hoveredStar: number = 0;
  auth = inject(Auth)

  song: any = null;
  searchService = inject(SearchService);
  comments: any[] = [];
  newComment: string = '';

  ngOnInit() {
    console.log('Fetching details for song ID:', this.songId);
    this.searchService.getSongById(this.songId!).then((response) => {
      console.log('Song details:', response);
      this.song = response;

      this.searchService.getComments(this.songId!).then((commentsResponse) => {
        this.comments = commentsResponse.content;
        this.crd.detectChanges();
      });
    });
  }

  onSearchInput() {
    if (this.searchQuery.trim()) {
      this.router.navigate(['/home'], { 
        queryParams: { q: this.searchQuery } 
      });
    }
  }

  rateSong(song: any, rating: number) {
    console.log(`Rating song ${song.songId} with ${rating} stars`);
    this.searchService.rateSong(song.songId, rating).then(() => {
      song.userRating = rating;
    });
  }

  addComment() {
    if (this.newComment.trim()) {
      console.log(`Adding comment to song ${this.songId}: ${this.newComment}`);
      this.searchService.addComment(this.songId!, this.newComment).then(() => {
        this.comments.push({
          content: this.newComment,
          username: 'You',
        });
        this.newComment = '';
        this.crd.detectChanges();
      });
    }
  }

  onUserClick() {
    this.auth.navigateToUserPage();
  }
}
