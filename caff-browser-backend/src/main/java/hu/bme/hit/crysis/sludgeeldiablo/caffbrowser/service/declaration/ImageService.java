package hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.service.declaration;

import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.dto.CommentDto;
import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.dto.ImageDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    /**
     * Saját kép törlése
     *
     * @param id a kép azonosítója
     */
    void deleteMyImage(Long id);

    /**
     * Kép törlése
     *
     * @param id a kép azonosítója
     */
    void delete(Long id);

    /**
     * Kép feltöltése
     *
     * @param file a feltöltött fűjl
     * @return a létrehozott kép adatai
     */
    ImageDto save(MultipartFile file);

    /**
     * Kép lekérdezse
     *
     * @param id a kép azonosítója
     * @return a lekérdezett kép adatai
     */
    ImageDto get(Long id);

    /**
     * Egy oldalnyi kép lekérdezése
     *
     * @return egy oldalnyi kép adatai
     */
    Page<ImageDto> getAll(Pageable pageable);

    /**
     * Hozzászólás írása egy képhez
     *
     * @param commentDto a hozzászólás adatai
     * @return a létrejött hozzászólás
     */
    CommentDto comment(CommentDto commentDto);
}
