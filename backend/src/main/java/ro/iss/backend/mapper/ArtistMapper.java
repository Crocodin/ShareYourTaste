package ro.iss.backend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ro.iss.backend.domain.Artist;
import ro.iss.backend.dto.ArtistDTO;

@Mapper(componentModel = "spring")
public interface ArtistMapper {
    ArtistDTO toDTO(Artist artist);
}
