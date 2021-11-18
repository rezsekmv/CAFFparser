package hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.repository;

import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
}
