package ro.iss.backend.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ro.iss.backend.domain.User;
import ro.iss.backend.dto.UserDTO;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toDTO(User user);
}
