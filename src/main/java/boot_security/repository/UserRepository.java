package boot_security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import boot_security.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
