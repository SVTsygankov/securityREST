package ru.SVTsygankov.securityREST.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.SVTsygankov.securityREST.model.User;
import ru.SVTsygankov.securityREST.model.Role;
import ru.SVTsygankov.securityREST.service.UserService;
import ru.SVTsygankov.securityREST.service.RoleService;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final RoleService roleService;
    private final UserService userService;

     public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping()
    public String showAll(@ModelAttribute("user") User user, Principal principal, Model model) {
        User authenticatedUser = userService.findByUsername(principal.getName()); //User который аутентифицировался
        model.addAttribute("authenticatedUser", authenticatedUser);
        model.addAttribute("rolesAuthenticatedUser", authenticatedUser.getRoles());
        model.addAttribute("users", userService.findAll());
        List<Role> roles = roleService.findAll();
        model.addAttribute("allRoles", roles);
        return "admin/adminShowAll";
    }

    @PostMapping("")
    public String saveUser(@ModelAttribute("user")  User user) {
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("user")  User user) {
        userService.updateUser(user, user.getId());
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}
