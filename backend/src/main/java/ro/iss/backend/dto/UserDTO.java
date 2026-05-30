package ro.iss.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class UserDTO {
    public int userId;
    public String username;
    public String email;
    public Integer followers;
    public Integer following;
    public String profilePictureUrl;
    public String spotifyLink;
    public String facebookLink;
    public String instagramLink;
}
