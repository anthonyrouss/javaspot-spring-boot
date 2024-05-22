package gr.unipi.javaspot.repositories;

import gr.unipi.javaspot.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

}
