package ro.iss.backend.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "song_ratings")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SongRating {

    @EmbeddedId
    private SongRatingId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("songId")
    @JoinColumn(name = "song_id")
    private Song song;

    @Column(name = "song_rating")
    private Integer rating;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;
}

