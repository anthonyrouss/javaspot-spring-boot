package gr.unipi.javaspot.repositories;

import gr.unipi.javaspot.models.Chapter;
import gr.unipi.javaspot.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByUnlockedChaptersContainsAndId(Chapter chapter, Integer id);

}
