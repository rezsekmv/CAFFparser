package hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.mapper;

import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.dto.ImageDto;
import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.model.Image;
import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.service.declaration.UserService;
import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.util.NativeParserUtil;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {CommentMapper.class})
public abstract class ImageMapper {

    @Autowired
    private UserService userService;

    @Mapping(expression = "java(getCommentsSize(entity))", target = "commentsSize")
    @Mapping(target = "comments", ignore = true)
    abstract public ImageDto toDto(Image entity);

    abstract public ImageDto toDtoWithComments(Image entity);

    @AfterMapping
    public void mapToDto(@MappingTarget ImageDto dto, Image entity) {
        dto.setUserDisplayName(getUserDisplayName(entity));
        dto.setGifPath(NativeParserUtil.getGifPath(entity.getUuid()));
        dto.setJsonPath(NativeParserUtil.getJsonPath(entity.getUuid()));
    }

    Integer getCommentsSize(Image entity) {
        return entity.getComments().size();
    }

    private String getUserDisplayName(Image entity) {
        return userService.findById(entity.getUserId()).getDisplayName();
    }
}
