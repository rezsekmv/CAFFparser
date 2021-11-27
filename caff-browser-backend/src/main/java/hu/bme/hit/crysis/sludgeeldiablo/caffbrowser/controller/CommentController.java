package hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.controller;

import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.dto.CommentDto;
import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.service.declaration.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
@Tag(name = "comment")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    @Operation(summary = "Hozzászólás az azonosító alapján megadott képhez")
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto) {
        log.trace("CommentController : createComment, commentDto=[{}]", commentDto);
        return new ResponseEntity<>(commentService.save(commentDto), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Hozzászólás eltávolítása")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        log.trace("CommentController : deleteComment, id=[{}]", id);
        commentService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Hozzászólás módosítása")
    public ResponseEntity<CommentDto> updateComment(@PathVariable Long id, @RequestBody CommentDto commentDto) {
        log.trace("CommentController : updateComment, id=[{}], commentDto=[{}]", id, commentDto);
        return ResponseEntity.ok(commentService.update(id, commentDto));
    }
}
