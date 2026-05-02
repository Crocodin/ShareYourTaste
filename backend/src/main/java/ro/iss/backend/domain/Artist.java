package ro.iss.backend.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "artists")
public class Artist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "artist_id")
    private int artistId;

    @NotNull
    @Column(name = "artist_name")
    private String artistName;

    @NotNull
    @Column(name = "artist_overall_rating")
    private String artistRating;
}
