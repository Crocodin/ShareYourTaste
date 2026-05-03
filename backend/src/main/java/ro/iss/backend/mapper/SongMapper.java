package ro.iss.backend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ro.iss.backend.domain.Song;
import ro.iss.backend.dto.SongDTO;

@Mapper(componentModel = "spring")
public interface SongMapper {

    @Mapping(source = "artist.artistId", target = "artistId")
    @Mapping(source = "artist.artistName", target = "artistName")
    @Mapping(source = "album.albumId", target = "albumId")
    @Mapping(source = "album.albumName", target = "albumName")
    @Mapping(source = "album.coverUrl", target = "coverUrl")
    SongDTO toDTO(Song song);
}