package ro.iss.backend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ro.iss.backend.domain.Comment;
import ro.iss.backend.dto.CommentDTO;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(source = "song.songId", target = "songId")
    @Mapping(source = "user.username", target = "username")
    CommentDTO toDto(Comment comment);
}
