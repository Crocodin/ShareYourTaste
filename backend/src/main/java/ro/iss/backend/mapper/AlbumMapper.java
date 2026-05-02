package ro.iss.backend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ro.iss.backend.domain.Album;
import ro.iss.backend.dto.AlbumDTO;

@Mapper(componentModel = "spring")
public interface AlbumMapper {

    @Mapping(source = "artist.artistId", target = "artistId")
    @Mapping(source = "artist.artistName", target = "artistName")
    AlbumDTO toDTO(Album album);
}