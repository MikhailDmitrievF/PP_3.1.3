package boot_security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import boot_security.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
