package hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.mapper;

import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.dto.ImageDto;
import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.model.Image;
import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.model.User;
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
    abstract public ImageDto toDto(Image entity);

    String getUserDisplayName(Image entity) {
        return entity.getUser().getDisplayName();
    }

    public Image toEntity(MultipartFile file) {
        // TODO: to be implemented instead of this dummy
        Image dummyImage = new Image();
        dummyImage.setUserId(userService.getCurrentUser().getId());
        dummyImage.setUuid(UUID.randomUUID().toString());
        return dummyImage;
    }

    @AfterMapping
    public ImageDto mapToDto(@MappingTarget Image entity, ImageDto dto) {
        // TODO: to be implemented instead of this dummy
        ImageDto dummyImageDto = new ImageDto();
        dummyImageDto.setGifPath("images/" + entity.getUuid() + ".gif");
        dummyImageDto.setJsonPath("images/" + entity.getUuid() + ".json");
        dummyImageDto.setUserDisplayName(entity.getUser().getDisplayName());
        return dummyImageDto;
    }
}
