package hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.service.implementation;

import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.dto.ImageDto;
import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.exception.CbException;
import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.exception.CbNativeParserException;
import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.exception.CbNotFoundException;
import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.mapper.ImageMapper;
import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.model.Image;
import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.repository.ImageRepository;
import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.service.declaration.ImageService;
import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.service.declaration.UserService;
import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.util.NativeParserUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;
    private final ImageMapper imageMapper;
    private final UserService userService;

    private Long getCurrentUserId() {
        return userService.getCurrentUser().getId();
    }

    @Override
    public void delete(Long id) {
        log.trace("ImageService : delete, id=[{}]", id);
        validateCanDelete(id);
        imageRepository.deleteById(id);
    }

    private void validateCanDelete(Long id) {
        if (!canCurrentUserModifyImage(id)) {
            throw new CbException("error.image.delete");
        }
    }

    @Override
    public Boolean canCurrentUserModifyImage(Long id) {
        return isOwnedByCurrentUser(id) || userService.isCurrentUserAdmin();
    }

    private Boolean isOwnedByCurrentUser(Long id) {
        return findById(id).getUserId().equals(getCurrentUserId());
    }

    @Override
    public Image findById(Long id) {
        log.trace("ImageService : findById, id=[{}]", id);
        return imageRepository.findById(id)
                .orElseThrow(CbNotFoundException::new);
    }

    @Override
    public ImageDto save(MultipartFile file) {
        log.trace("ImageService : save, file=[{}]", file);
        Image createdImage = imageRepository.save(createImage(file));
        return imageMapper.toDto(createdImage);
    }

    private Image createImage(MultipartFile file) {
        Image image = parseFile(file);
        setCurrentUser(image);
        return image;
    }

    private Image parseFile(MultipartFile file) {
        try {
            return NativeParserUtil.parse(file);
        } catch (Throwable t) {
            throw new CbNativeParserException(t.getMessage());
        }
    }

    private void setCurrentUser(Image image) {
        image.setUserId(userService.getCurrentUser().getId());
    }

    @Override
    public ImageDto get(Long id) {
        log.trace("ImageService : get, id=[{}]", id);
        return imageMapper.toDtoWithDetails(findById(id));
    }

    @Override
    public Page<ImageDto> getAll(Pageable pageable) {
        log.trace("ImageService : getAll, pageable=[{}]", pageable);
        return imageRepository.findAll(pageable).map(imageMapper::toDto);
    }

    @Override
    public Boolean canCurrentUserCommentImage(Long imageId) {
        return userService.isCurrentUserAdmin() || !isCurrentUserCommentedAlready(imageId);
    }

    private Boolean isCurrentUserCommentedAlready(Long imageId) {
        return findById(imageId).getComments().stream()
                .anyMatch(c -> c.getUserId().equals(getCurrentUserId()));
    }
}
