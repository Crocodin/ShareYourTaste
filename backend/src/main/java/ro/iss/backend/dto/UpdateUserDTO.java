package ro.iss.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserDTO {
    private String spotifyLink;
    private String instagramLink;
    private String facebookLink;
    private String profilePictureUrl;
}
