import { ChangeDetectorRef, Component, inject } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { Auth } from '../../services/auth';
import { environment } from '../../environments/environment';
import { User } from '../../model/user.model';
import { Song } from '../../model/song.model';
import { Comment } from '../../model/comment.model';
import { SearchService } from '../../services/search';
import { DecimalPipe } from '@angular/common';

@Component({
  selector: 'app-profile',
  imports: [FormsModule, DecimalPipe],
  templateUrl: './profile.html',
  styleUrl: './profile.css',
})
export class Profile {
  route = inject(ActivatedRoute);
  router = inject(Router);
  auth = inject(Auth);
  searchService = inject(SearchService);
  http = inject(HttpClient);
  cdr = inject(ChangeDetectorRef);

  userId: number = parseInt(this.route.snapshot.paramMap.get('id') || '0');
  currentUserId: number = this.auth.getUserId() ?? -1;

  user: User | null = null;
  recentActivities: Song[] = [];
  comments: Comment[] = [];

  isLoading: boolean = true;
  error: string = '';
  isFollowing: boolean = false;
  isEditing: boolean = false;

  searchQuery: string = '';
  newComment: string = '';

  editForm = {
    spotifyLink: '',
    instagramLink: '',
    facebookLink: '',
    profilePictureUrl: '',
  };

  songsById: Record<number, Song> = {};

  ngOnInit() {
    this.loadProfile();
    this.loadActivity();
    this.loadComments();
  }

  loadProfile() {
    this.auth.findUserById(this.userId).then((user: User) => {
      this.user = user;
      this.editForm = {
        spotifyLink: user?.spotifyLink || '',
        instagramLink: user?.instagramLink || '',
        facebookLink: user?.facebookLink || '',
        profilePictureUrl: user?.profilePictureUrl || '',
      };
      this.isLoading = false;
      this.cdr.detectChanges();
    }).catch(() => {
      this.error = 'Could not load profile.';
      this.isLoading = false;
      this.cdr.detectChanges();
    });
  }

  loadActivity() {
    this.searchService.findUserActivities(this.userId).then((activities) => {
      this.recentActivities = activities;
      this.cdr.detectChanges();
    }).catch(() => {
      // Just fail silently if we can't load activities, it's not a critical part of the profile
    });
  }

  loadComments() {
    this.searchService.findUserComments(this.userId).then(async (_comments) => {
      this.comments = _comments;
      this.cdr.detectChanges();
      for (const comment of this.comments) {
        if (!this.songsById[comment.songId]) {
          this.songsById[comment.songId] =
            await this.searchService.getSongById(comment.songId);
        }
      }
      this.cdr.detectChanges();
    });
  }

  async onAvatarSelected(event: Event) {
    const input = event.target as HTMLInputElement;
    if (!input.files?.length) return;

    const file = input.files[0];
    const formData = new FormData();
    formData.append('file', file);

    this.auth.saveProfileAvatar(file).then((path) => {
      this.editForm.profilePictureUrl = path;
      this.cdr.detectChanges();
    }).catch((e) => {
      alert('Failed to upload avatar.' + e.message);
    });
  }

  openEdit() {
    this.isEditing = this.user !== null;
  }

  cancelEdit() {
    if (!this.user) return; // this will never happen because the edit button is hidden when user is null, but just to stop the ts error
    this.isEditing = false;
    // reset form back to current user values
    this.editForm = {
      spotifyLink: this.user.spotifyLink || '',
      instagramLink: this.user.instagramLink || '',
      facebookLink: this.user.facebookLink || '',
      profilePictureUrl: this.user.profilePictureUrl || '',
    };
  }

  async saveProfile() {
    this.auth.updateProfile(this.editForm).then((updatedUser) => {
      this.user = updatedUser;
      this.auth.setUser(updatedUser); // Keep the auth service in sync so the navbar avatar updates too
      this.cdr.detectChanges();
    }).catch((e) => {
      alert('Failed to update profile.' + e.message);
    });

    this.isEditing = false;
    this.cdr.detectChanges();
  }

  async toggleFollow() {
    if (this.isFollowing) {
      this.auth.unfollowUser(this.userId).then(() => {
        if (this.user) {
          this.user.followers = (this.user.followers ?? 1) - 1;
        }
        this.cdr.detectChanges();
      }).catch((e) => {
        alert('Failed to unfollow user.' + e.message);
      }); 
    } else {
      this.auth.followUser(this.userId).then(() => {
        if (this.user) {
          this.user.followers = (this.user.followers ?? 0) + 1;
        }
        this.cdr.detectChanges();
      }).catch((e) => {
        alert('Failed to follow user.' + e.message);
      });
    }
    this.isFollowing = !this.isFollowing;
    this.cdr.detectChanges();
  }

  /* Turns a stored path like /avatars/foo.jpg into a full URL for <img src> */
  avatarSrc(path: string | null): string {
    if (!path) return '';
    if (path.startsWith('http')) return path;
    return `${environment.apiUrl}${path}`;
  }

  starsArray(rating: number): boolean[] {
    return [1, 2, 3, 4, 5].map(n => n <= Math.round(rating || 0));
  }

  onSearchInput() {
    if (this.searchQuery.trim()) {
      this.router.navigate(['/home'], { queryParams: { q: this.searchQuery } });
    }
  }

  onUserClick() {
    this.auth.navigateToUserPage();
  }
}
