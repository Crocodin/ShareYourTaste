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
public class AlbumDTO {
    private int albumId;

    @NotNull private String albumName;
    private int albumRating;
    private String coverUrl;

    private int artistId;
    private String artistName;
}
