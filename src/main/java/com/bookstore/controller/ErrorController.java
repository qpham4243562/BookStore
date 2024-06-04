package com.bookstore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/error")
public class ErrorController {

    @GetMapping("/403")
    public String accessDenied403() {
        return "error/403";
    }

    @GetMapping("/404")
    public String accessDenied404() {
        return "error/404";
    }

    @GetMapping("/400")
    public String accessDenied400() {
        return "error/400";
    }

    @GetMapping("/500")
    public String accessDenied500() {
        return "error/500";
    }
}
