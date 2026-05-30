
export interface Song {
  songId: number;
  songName: string;
  songRating: number;
  userRating: number;

  artistId: number;
  artistName: string;

  albumId: number;
  albumName: string;
  coverUrl: string;

  hoveredRating: number;
}