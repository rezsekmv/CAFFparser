package hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.controller;

import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.dto.CommentDto;
import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.dto.ImageDto;
import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.service.declaration.ImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/api/image")
@RequiredArgsConstructor
@Tag(name = "image")
public class ImageController {

    private final ImageService imageService;

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "Új kép létrehozása")
    public ResponseEntity<ImageDto> createImage(@RequestPart("image") MultipartFile file) {
        log.trace("ImageController : createImage, file=[{}]", file);
        return new ResponseEntity<>(imageService.save(file), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Kép és annak részleteinek megtekintése azonosító alapján")
    public ResponseEntity<ImageDto> getImage(@PathVariable Long id) {
        log.trace("ImageController : getImage, id=[{}]", id);
        return ResponseEntity.ok(imageService.get(id));
    }

    @GetMapping
    @Operation(summary = "Összes kép megtekintése")
    public ResponseEntity<Page<ImageDto>> getAllImage(@ParameterObject Pageable pageable) {
        log.trace("ImageController : getAllImage");
        return ResponseEntity.ok(imageService.getAll(pageable));
    }

    @PostMapping("/comment")
    @Operation(summary = "Hozzászólás az azonosító alapján megadott képhez")
    public ResponseEntity<CommentDto> commentImage(@RequestBody CommentDto commentDto) {
        log.trace("ImageController : commentImage, commentDto=[{}]", commentDto);
        return new ResponseEntity<>(imageService.comment(commentDto), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Saját kép eltávolítása")
    public ResponseEntity<Void> deleteMyImage(@PathVariable Long id) {
        log.trace("ImageController : deleteMyImage, id=[{}]", id);
        imageService.deleteMyImage(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
