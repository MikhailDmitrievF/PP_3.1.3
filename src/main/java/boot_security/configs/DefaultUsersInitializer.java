package boot_security.configs;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import boot_security.model.Role;
import boot_security.model.User;
import boot_security.service.UserService;
import boot_security.service.RoleService;

import java.util.HashSet;
import java.util.Set;

@Component
public class DefaultUsersInitializer {
    private final UserService userService;
    private final RoleService roleService;

    public DefaultUsersInitializer(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostConstruct
    public void init() {
        try {
            if (userService.getAll().isEmpty()) {
                Role roleAdmin = new Role("ROLE_ADMIN");
                Role roleUser = new Role("ROLE_USER");
                
                roleService.saveRole(roleAdmin);
                roleService.saveRole(roleUser);

                User admin = new User();
                admin.setUsername("admin");
                admin.setSurname("admin");
                admin.setAge(30);
                admin.setPassword("admin");
                Set<Role> adminRoles = new HashSet<>();
                adminRoles.add(roleAdmin);
                admin.setRoles(adminRoles);

                User user = new User();
                user.setUsername("user");
                user.setSurname("user");
                user.setAge(25);
                user.setPassword("user");
                Set<Role> userRoles = new HashSet<>();
                userRoles.add(roleUser);
                user.setRoles(userRoles);

                userService.add(admin);
                userService.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
