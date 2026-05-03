package ro.iss.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tools.jackson.databind.annotation.JsonDeserialize;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@JsonDeserialize(builder = CommentDTO.CommentDTOBuilder.class)
public class CommentDTO {
    private int commentId;
    @JsonProperty("content") private String content;
    private String username;
    private int songId;
}
