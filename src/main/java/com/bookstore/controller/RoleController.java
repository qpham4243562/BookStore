package com.bookstore.controller;

import com.bookstore.services.RoleService;
import com.bookstore.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/roles")
@PreAuthorize("hasAuthority('ADMIN')")
public class RoleController {

    @Autowired
    private UserServices userService;

    @Autowired
    private RoleService roleService;

    @GetMapping
    public String manageRoles(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("roles", roleService.getAllRoles());
        return "admin/manage-roles";
    }

    @PostMapping("/assign")
    public String assignRole(@RequestParam Long userId, @RequestParam Long roleId, Model model) {
        String message = userService.assignRoleToUser(userId, roleId);
        model.addAttribute("message", message);
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("roles", roleService.getAllRoles());
        return "admin/manage-roles";
    }
}
