package hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.mapper;

import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.dto.ImageDto;
import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.model.Image;
import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.service.declaration.ImageService;
import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.service.declaration.UserService;
import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.util.NativeParserUtil;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {CommentMapper.class})
public abstract class ImageMapper {

    @Autowired
    private UserService userService;

    @Autowired @Lazy
    private ImageService imageService;

    @Mapping(expression = "java(getCommentsSize(entity))", target = "commentsSize")
    @Mapping(target = "comments", ignore = true)
    abstract public ImageDto toDto(Image entity);

    @Mapping(expression = "java(isCommentable(entity))", target = "commentable")
    @Mapping(expression = "java(isModifiable(entity))", target = "modifiable")
    abstract public ImageDto toDtoWithDetails(Image entity);

    @AfterMapping
    public void mapToDto(@MappingTarget ImageDto dto, Image entity) {
        dto.setUserDisplayName(getUserDisplayName(entity));
        dto.setGifPath(NativeParserUtil.getGifPath(entity.getUuid()));
        dto.setCaffPath(NativeParserUtil.getCaffPath(entity.getUuid()));
    }

    Integer getCommentsSize(Image entity) {
        return entity.getComments().size();
    }

    Boolean isCommentable(Image entity) {
        return imageService.canCurrentUserCommentImage(entity.getId());
    }

    Boolean isModifiable(Image entity) {
        return imageService.canCurrentUserModifyImage(entity.getId());
    }

    private String getUserDisplayName(Image entity) {
        return userService.findById(entity.getUserId()).getDisplayName();
    }
}
