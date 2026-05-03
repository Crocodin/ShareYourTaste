package ro.iss.backend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class SongDTO {
    private int songId;

    @NotNull private String songName;
    private double songRating;
    private double userRating;

    private int artistId;
    private String artistName;

    private int albumId;
    private String albumName;
    private String coverUrl;
}
