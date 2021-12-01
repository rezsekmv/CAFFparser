package hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.service.declaration;

import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.dto.ImageDto;
import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.dto.ImageQueryDto;
import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.model.Image;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    /**
     * Kép törlése
     *
     * @param id a kép azonosítója
     */
    void delete(Long id);

    /**
     * Visszaadja azonosító alapján a kép entitást
     *
     * @param id megadott azonosító
     * @return kép entitás
     */
    Image findById(Long id);

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
     * @param pageable lapozási paraméterek
     * @param query keresési paraméterek
     * @return egy oldalnyi kép adatai
     */
    Page<ImageDto> getAll(Pageable pageable, ImageQueryDto query);

    /**
     * Visszaadja, hogy a bejelentkezett felhasználó módosíthatja-e a megadott azonosítójú képet
     *
     * @param id a kép azonosítója
     * @return logikai érték
     */
    Boolean canCurrentUserModifyImage(Long id);

    /**
     * Visszaadja, hogy a bejelentkezett felhasználó hozzászólhat-e a megadott azonosítójú képhez
     *
     * @param imageId kép azonosítója
     * @return logikai érték
     */
    Boolean canCurrentUserCommentImage(Long imageId);
}
