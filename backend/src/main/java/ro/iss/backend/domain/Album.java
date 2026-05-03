package ro.iss.backend.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "albums")
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "album_id")
    private int albumId;

    @Column(name = "album_name")
    private String albumName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "album_artist_id")
    private Artist artist;

    @Column(name = "album_overall_rating")
    private int albumRating;

    @Column(name = "cover_url")
    private String coverUrl;
}
