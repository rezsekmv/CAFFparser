package hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.mapper;

import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.dto.CommentDto;
import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.model.Comment;
import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.service.declaration.CommentService;
import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.service.declaration.UserService;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class CommentMapper {

    @Autowired
    private UserService userService;

    @Lazy
    @Autowired
    private CommentService commentService;

    @Mapping(expression = "java(getUserDisplayName(entity))", target = "userDisplayName")
    public abstract CommentDto toDto(Comment entity);

    @Named("toDtoListed")
    @Mapping(target = "imageId", ignore = true)
    @Mapping(expression = "java(getUserDisplayName(entity))", target = "userDisplayName")
    @Mapping(expression = "java(isModifiable(entity))", target = "modifiable")
    public abstract CommentDto toDtoListed(Comment entity);

    @IterableMapping(qualifiedByName = "toDtoListed")
    public abstract List<CommentDto> toDtoList(List<Comment> entities);

    @Mapping(target = "id", ignore = true)
    @Mapping(expression = "java(getCurrentUserId())", target = "userId")
    public abstract Comment toEntity(CommentDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "imageId", ignore = true)
    public abstract Comment update(@MappingTarget Comment entity, CommentDto dto);

    Long getCurrentUserId() {
        return userService.getCurrentUser().getId();
    }

    String getUserDisplayName(Comment entity) {
        return userService.findById(entity.getUserId()).getDisplayName();
    }

    Boolean isModifiable(Comment entity) {
        return commentService.canCurrentUserModifyComment(entity.getId());
    }
}
