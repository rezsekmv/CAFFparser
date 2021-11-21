package hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.controller;

import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.dto.ImageDto;
import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.service.declaration.ImageService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public ResponseEntity<ImageDto> createImage(@RequestPart("image") MultipartFile file) {
        log.trace("ImageController : createImage, file=[{}]", file);
        return new ResponseEntity<>(imageService.save(file), HttpStatus.CREATED);
    }

    // TODO: getImage

    // TODO: getAllImage

    // TODO: commentImage

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMyImage(@PathVariable Long id) {
        log.trace("ImageController : deleteMyImage, id=[{}]", id);
        imageService.deleteMyImage(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
