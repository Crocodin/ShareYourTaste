package ro.iss.backend.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Formula;

@Data
@Entity
@Table(name = "users")
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "user_name")
    private String username;

    @Column(name = "user_password")
    private String password;

    @Column(name = "user_email")
    private String email;

    @Formula("(SELECT COUNT(*) FROM user_following uf WHERE uf.followed = user_id)")
    private Integer followers;

    @Formula("(SELECT COUNT(*) FROM user_following uf WHERE uf.follower = user_id)")
    private Integer following;

    @Size(max = 255)
    @Column(name = "profile_picture_url")
    private String profilePictureUrl;

    @Size(max = 255)
    @Column(name = "spotify_link")
    private String spotifyLink;

    @Size(max = 255)
    @Column(name = "facebook_link")
    private String facebookLink;

    @Size(max = 255)
    @Column(name = "instagram_link")
    private String instagramLink;
}