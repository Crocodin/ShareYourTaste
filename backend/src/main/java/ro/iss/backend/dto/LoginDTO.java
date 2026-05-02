package ro.iss.backend.dto;

import lombok.*;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class LoginDTO {
    private String username;
    private String password;
}