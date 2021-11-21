package hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.repository;

import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.model.Image;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {

    Page<Image> findAll(Pageable pageable);
}
