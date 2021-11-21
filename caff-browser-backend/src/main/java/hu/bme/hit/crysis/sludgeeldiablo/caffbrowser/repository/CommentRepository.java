package hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.repository;

import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
