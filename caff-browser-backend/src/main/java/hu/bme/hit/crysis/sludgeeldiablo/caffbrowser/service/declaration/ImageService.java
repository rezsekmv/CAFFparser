package hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.service.declaration;

import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.dto.ImageDto;
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
}
