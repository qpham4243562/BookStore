package com.bookstore.controller;

import com.bookstore.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

@Controller
public class ResetPasswordController {

    @Autowired
    private UserServices userServices;

    @GetMapping("/reset-password")
    public String showResetPasswordForm(@RequestParam("token") String token, Model model) {
        if (!userServices.isValidToken(token)) {
            model.addAttribute("error", "Token không hợp lệ hoặc đã hết hạn.");
            return "reset-password";
        }
        model.addAttribute("token", token);
        return "reset-password";
    }

    @PostMapping("/reset-password")
    public String processResetPassword(@RequestParam("token") String token,
                                       @RequestParam("password") String password, Model model) {
        if (userServices.updatePassword(token, password)) {
            model.addAttribute("message", "Mật khẩu đã được đặt lại thành công!");
        } else {
            model.addAttribute("error", "Token không hợp lệ hoặc đã hết hạn.");
        }
        return "reset-password";
    }
}
