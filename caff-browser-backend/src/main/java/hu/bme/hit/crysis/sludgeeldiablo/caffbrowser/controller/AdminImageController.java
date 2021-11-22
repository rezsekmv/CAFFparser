package hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.controller;

import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.service.declaration.ImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/admin/image")
@RequiredArgsConstructor
@Tag(name = "admin")
public class AdminImageController {

    private final ImageService imageService;

    @DeleteMapping("/{id}")
    @Operation(summary = "Kép eltávolítása")
    public ResponseEntity<Void> deleteImage(@PathVariable Long id) {
        log.trace("AdminImageController : deleteImage, id=[{}]", id);
        imageService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
