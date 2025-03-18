package boot_security.controller;

import boot_security.model.Role;
import boot_security.model.User;
import boot_security.service.RoleService;
import boot_security.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String showAllUsers(Model model) {
        model.addAttribute("allUsers", userService.getAll());
        model.addAttribute("user", new User());
        model.addAttribute("allRoles", roleService.getAllRoles());
        return "admin/users";
    }

    @PostMapping("/add")
    public String addUser(@Valid @ModelAttribute("user") User user,
                          BindingResult bindingResult,
                          @RequestParam Set<Long> roles,
                          Model model,
                          RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("allUsers", userService.getAll());
            model.addAttribute("allRoles", roleService.getAllRoles());
            return "admin/users";
        }

        Set<Role> roleSet = roles.stream()
                .map(roleService::getRoleById)
                .collect(Collectors.toSet());
        user.setRoles(roleSet);

        userService.add(user);
        redirectAttributes.addFlashAttribute("success", "User added successfully");
        return "redirect:/admin";
    }

    @PostMapping("/update")
    public String updateUser(@Valid @ModelAttribute("user") User user,
                             BindingResult bindingResult,
                             @RequestParam(value = "roleIds", required = false) Set<Long> roleIds,
                             RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("error", "Validation error");
            return "redirect:/admin";
        }

        User existingUser = userService.findById(user.getId());
        if (existingUser == null) {
            redirectAttributes.addFlashAttribute("error", "User not found");
            return "redirect:/admin";
        }

        existingUser.setUsername(user.getUsername());
        existingUser.setSurname(user.getSurname());
        existingUser.setAge(user.getAge());

        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            existingUser.setPassword(user.getPassword());
        }

        if (roleIds != null && !roleIds.isEmpty()) {
            Set<Role> newRoles = roleIds.stream()
                    .map(roleService::getRoleById)
                    .collect(Collectors.toSet());
            existingUser.setRoles(newRoles);
        }

        userService.update(existingUser);
        redirectAttributes.addFlashAttribute("success", "User updated successfully");
        return "redirect:/admin";
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam("id") long id,
                             RedirectAttributes redirectAttributes) {
        User user = userService.findById(id);
        if (user == null) {
            redirectAttributes.addFlashAttribute("error", "User not found");
            return "redirect:/admin";
        }

        userService.delete(id);
        redirectAttributes.addFlashAttribute("success", "User deleted successfully");
        return "redirect:/admin";
    }
}
