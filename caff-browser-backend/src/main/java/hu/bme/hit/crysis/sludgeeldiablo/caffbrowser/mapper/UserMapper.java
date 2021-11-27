package hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.mapper;

import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.dto.UserDto;
import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.enums.RoleName;
import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.model.Role;
import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class UserMapper {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Mapping(expression = "java(getRoleNames(entity))", target = "roles")
    @Mapping(target = "password", ignore = true)
    abstract public UserDto toDto(User entity);

    abstract public List<UserDto> toDtoList(List<User> user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(expression = "java(getEncodedPassword(dto))", target = "password")
    abstract public User toEntity(UserDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "username", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "roles", ignore = true)
    abstract public User update(@MappingTarget User entity, UserDto dto);

    Set<RoleName> getRoleNames(User entity) {
        return entity.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toSet());
    }

    String getEncodedPassword(UserDto dto) {
        return passwordEncoder.encode(dto.getPassword());
    }
}
