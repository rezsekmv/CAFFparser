package hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.mapper;

import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.dto.ImageDto;
import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.model.Image;
import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.service.declaration.ImageService;
import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.service.declaration.UserService;
import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.util.NativeParserUtil;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {CommentMapper.class})
public abstract class ImageMapper {

    private static final String IMAGE_PATH_REGEX = "/**";

    @Value("${spring.mvc.static-path-pattern}")
    private String imagesPath;

    @Autowired
    private UserService userService;

    @Lazy
    @Autowired
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
        dto.setGifPath(getGifPath(entity));
        dto.setCaffPath(getCaffPath(entity));
    }

    private String getGifPath(Image entity) {
        return imagesPath.replace(IMAGE_PATH_REGEX, NativeParserUtil.getGifPath(entity.getUuid()));
    }

    private String getCaffPath(Image entity) {
        return imagesPath.replace(IMAGE_PATH_REGEX, NativeParserUtil.getCaffPath(entity.getUuid()));
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
