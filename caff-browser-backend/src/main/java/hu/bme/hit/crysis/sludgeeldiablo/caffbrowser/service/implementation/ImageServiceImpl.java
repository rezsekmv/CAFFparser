package hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.service.implementation;

import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.dto.CommentDto;
import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.dto.ImageDto;
import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.exception.CbException;
import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.exception.CbNativeParserException;
import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.exception.CbNotFoundException;
import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.mapper.CommentMapper;
import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.mapper.ImageMapper;
import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.model.Comment;
import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.model.Image;
import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.repository.CommentRepository;
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
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final UserService userService;

    @Override
    public void deleteMyImage(Long id) {
        log.trace("ImageService : deleteMyImage, id=[{}]", id);
        Image image = findById(id);
        validateMyImage(image);
        imageRepository.delete(image);
    }

    private void validateMyImage(Image image) {
        if (!image.getUser().getId().equals(getCurrentUserId())) {
            throw new CbException("error.image.delete");
        }
    }

    private Long getCurrentUserId() {
        return userService.getCurrentUser().getId();
    }

    @Override
    public void delete(Long id) {
        log.trace("ImageService : delete, id=[{}]", id);
        imageRepository.delete(findById(id));
    }

    private Image findById(Long id) {
        return imageRepository.findById(id)
                .orElseThrow(CbNotFoundException::new);
    }

    @Override
    public ImageDto save(MultipartFile file) {
        log.trace("ImageService : save, file=[{}]", file);
        String uuid = parseFile(file);
        Image createdImage = imageRepository.save(createImage(uuid));
        return imageMapper.toDto(createdImage);
    }

    private String parseFile(MultipartFile file) {
        try {
            return NativeParserUtil.parse(file);
        } catch (Throwable t) {
            throw new CbNativeParserException(t.getMessage());
        }
    }

    private Image createImage(String uuid) {
        Image image = new Image();
        image.setUuid(uuid);
        image.setUserId(userService.getCurrentUser().getId());
        return image;
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
    public CommentDto comment(CommentDto commentDto) {
        log.trace("ImageService : comment, commentDto=[{}]", commentDto);
        validateCanComment(commentDto.getImageId());
        Comment createdComment = commentRepository.save(commentMapper.toEntity(commentDto));
        return commentMapper.toDto(createdComment);
    }

    private void validateCanComment(Long imageId) {
        if (!canCurrentUserCommentImage(imageId)) {
            throw new CbException("error.image.comment");
        }
    }

    private Boolean canCurrentUserCommentImage(Long imageId) {
        return userService.isCurrentUserAdmin() || !isCurrentUserCommentedAlready(imageId);
    }

    private Boolean isCurrentUserCommentedAlready(Long imageId) {
        return findById(imageId).getComments().stream()
                .anyMatch(c -> c.getUserId().equals(getCurrentUserId()));
    }
}
