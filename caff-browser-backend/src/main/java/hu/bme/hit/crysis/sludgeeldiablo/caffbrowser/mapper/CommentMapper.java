package hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.mapper;

import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.dto.CommentDto;
import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.model.Comment;
import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.model.Image;
import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.service.declaration.UserService;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class CommentMapper {

    @Autowired
    private UserService userService;

    @Mapping(expression = "java(getUserDisplayName(entity))", target = "userDisplayName")
    abstract public CommentDto toDto(Comment entity);

    @Named("toDtoListed")
    @Mapping(target = "imageId", ignore = true)
    @Mapping(expression = "java(getUserDisplayName(entity))", target = "userDisplayName")
    abstract public CommentDto toDtoListed(Comment entity);

    @IterableMapping(qualifiedByName = "toDtoListed")
    abstract public List<CommentDto> toDtoList(List<Comment> entities);

    @Mapping(expression = "java(getCurrentUserId())", target = "userId")
    abstract public Comment toEntity(CommentDto dto);

    Long getCurrentUserId() {
        return userService.getCurrentUser().getId();
    }

    String getUserDisplayName(Comment entity) {
        return userService.findById(entity.getUserId()).getDisplayName();
    }
}
