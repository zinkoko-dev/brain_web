package com.group.brain_web.controller;

import com.group.brain_web.service.password.reset.PasswordResetService;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@AllArgsConstructor
@Controller
@RequestMapping("/password/reset")
public class PasswordResetController {

    private final PasswordResetService passwordResetService;

    @GetMapping("/form")
    public String form() {
        return "password.reset/form";
    }

    @PostMapping("/form")
    public String formPost(@RequestParam String email) {
        return passwordResetService.passwordReset(email);
    }

    @GetMapping("/save/{id}")
    public String save(@PathVariable int id, Model model) {
        model.addAttribute("save", id);
        return "password.reset/save";
    }

    @PostMapping("/save")
    public String savePost(@RequestParam String password,@RequestParam int id) {
        return passwordResetService.passwordResetConfirm(password,id);
    }

}
