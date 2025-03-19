package boot_security.controller;

import boot_security.model.User;
import boot_security.service.RoleService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    private final RoleService roleService;

    public UserController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public String showUserInfo(Model model, Authentication authentication) {
        User currentUser = (User) authentication.getPrincipal();
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("allRoles", roleService.getAllRoles());
        return "user";
    }
}
