package boot_security.service;

import boot_security.model.Role;
import java.util.List;

public interface RoleService {
    void saveRole(Role role);
    Role findByName(String name);
    Role getRoleById(Long id);
    List<Role> getAllRoles();
}
