package hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.mapper;

import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.dto.ImageDto;
import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.model.Image;
import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.service.declaration.UserService;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {CommentMapper.class})
public abstract class ImageMapper {

    @Autowired
    private UserService userService;

    // TODO: natív komponens beemelése és függvények implementálása

    @Mapping(expression = "java(getUserDisplayName(entity))", target = "userDisplayName")
    @Mapping(expression = "java(getCommentNumber(entity))", target = "commentNumber")
    @Mapping(target = "comments", ignore = true)
    abstract public ImageDto toDto(Image entity);

    @Mapping(expression = "java(getUserDisplayName(entity))", target = "userDisplayName")
    @Mapping(expression = "java(mapComments(entity, pageable))", target = "comments")
    abstract public ImageDto toDtoWithComments(Image entity);

    String getUserDisplayName(Image entity) {
        return userService.findById(entity.getUserId()).getDisplayName();
    }

    Integer getCommentNumber(Image entity) {
        return entity.getComments().size();
    }

    public Image toEntity(MultipartFile file) {
        // TODO: to be implemented instead of this dummy
        Image dummyImage = new Image();
        dummyImage.setUserId(userService.getCurrentUser().getId());
        dummyImage.setUuid(UUID.randomUUID().toString());
        return dummyImage;
    }

    @AfterMapping
    public void mapToDto(@MappingTarget ImageDto dummyImageDto, Image entity) {
        // TODO: to be implemented instead of this dummy
        dummyImageDto.setGifPath("images/" + entity.getUuid() + ".gif");
        dummyImageDto.setJsonPath("images/" + entity.getUuid() + ".json");
    }
}
