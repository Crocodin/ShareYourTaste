package ro.iss.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.iss.backend.domain.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findUserByUsernameAndPassword(String username, String password);
}
