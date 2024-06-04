package com.bookstore.controller;

import com.bookstore.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

@Controller
public class ForgotPasswordController {

    @Autowired
    private UserServices userServices;

    @GetMapping("/forgot-password")
    public String showForgotPasswordForm() {
        return "forgot-password";
    }

    @PostMapping("/forgot-password")
    public String processForgotPassword(@RequestParam("email") String email, Model model) {
        boolean result = userServices.processForgotPassword(email);
        if (result) {
            model.addAttribute("message", "Đã gửi email đặt lại mật khẩu!");
        } else {
            model.addAttribute("error", "Email không tồn tại trong hệ thống!");
        }
        return "forgot-password";
    }
}
