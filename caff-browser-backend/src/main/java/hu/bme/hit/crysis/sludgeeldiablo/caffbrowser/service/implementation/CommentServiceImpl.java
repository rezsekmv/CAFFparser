package hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.service.implementation;

import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.dto.CommentDto;
import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.exception.CbException;
import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.exception.CbNotFoundException;
import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.mapper.CommentMapper;
import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.model.Comment;
import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.repository.CommentRepository;
import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.service.declaration.CommentService;
import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.service.declaration.ImageService;
import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.service.declaration.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final UserService userService;
    private final ImageService imageService;

    @Override
    public CommentDto save(CommentDto commentDto) {
        log.trace("CommentService : save, commentDto=[{}]", commentDto);
        validateImageExist(commentDto.getImageId());
        validateCanComment(commentDto.getImageId());
        Comment createdComment = commentRepository.save(commentMapper.toEntity(commentDto));
        return commentMapper.toDto(createdComment);
    }

    private void validateImageExist(Long imageId) {
        imageService.findById(imageId);
    }

    private void validateCanComment(Long imageId) {
        if (Boolean.FALSE.equals(imageService.canCurrentUserCommentImage(imageId))) {
            throw new CbException("error.comment.unable");
        }
    }

    @Override
    public CommentDto update(Long id, CommentDto commentDto) {
        log.trace("CommentService : update, id=[{}], commentDto=[{}]", id, commentDto);
        validateCanUpdate(id);
        Comment updatedComment = commentMapper.update(findById(id), commentDto);
        return commentMapper.toDto(updatedComment);
    }

    private void validateCanUpdate(Long id) {
        if (Boolean.FALSE.equals(canCurrentUserModifyComment(id))) {
            throw new CbException("error.comment.update");
        }
    }

    @Override
    public Boolean canCurrentUserModifyComment(Long id) {
        return isOwnedByCurrentUser(id) || userService.isCurrentUserAdmin();
    }

    private Boolean isOwnedByCurrentUser(Long id) {
        return findById(id).getUserId().equals(getCurrentUserId());
    }

    private Long getCurrentUserId() {
        return userService.getCurrentUser().getId();
    }

    private Comment findById(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(CbNotFoundException::new);
    }

    @Override
    public void delete(Long id) {
        log.trace("CommentService : delete, id=[{}]", id);
        validateCanDelete(id);
        commentRepository.deleteById(id);
    }

    private void validateCanDelete(Long id) {
        if (Boolean.FALSE.equals(canCurrentUserModifyComment(id))) {
            throw new CbException("error.comment.delete");
        }
    }
}
