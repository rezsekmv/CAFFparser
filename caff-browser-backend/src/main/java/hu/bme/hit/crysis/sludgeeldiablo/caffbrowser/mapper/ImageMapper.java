package hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.mapper;

import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.dto.ImageDto;
import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.model.Image;
import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.model.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {CommentMapper.class})
public abstract class ImageMapper {

    // TODO: natív komponens beemelése és függvények implementálása

    @Mapping(expression = "java(getUserDisplayName(entity))", target = "userDisplayName")
    abstract public ImageDto toDto(Image entity); // TODO: to be implemeted

    public Image toEntity(ImageDto imageDto) { // TODO: to be implemeted
        return new Image();
    }

    String getUserDisplayName(Image entity) {
        return entity.getUser().getDisplayName();
    }
}
