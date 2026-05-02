package ro.iss.backend.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "songs")
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "song_id")
    private Long songId;

    @Column(name = "song_name")
    private String songName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "song_artist_id")
    private Artist artist;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "song_album_id")
    private Album album;

    @Column(name = "song_rating")
    private double songRating;
}
