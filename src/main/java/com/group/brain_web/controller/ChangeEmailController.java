package com.group.brain_web.controller;

import com.group.brain_web.service.change.email.EmailChangeService;
import com.group.brain_web.service.email.EmailService;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@AllArgsConstructor
@Controller
@RequestMapping("/email")
public class ChangeEmailController {

    private final EmailChangeService changeEmail;

    @GetMapping("/form")
    public String form() {
        return "email.change/form";
    }

    @PostMapping("/form")
    public String formPost(@RequestParam String email) throws UnsupportedEncodingException, MessagingException {
        return changeEmail.changeEmail(email);
    }

    @GetMapping("/save/{id}")
    public String save(@PathVariable int id, Model model) {
        model.addAttribute("save", id);
        return "email.change/save";
    }

    @PostMapping("/save")
    public String savePost(@RequestParam String email, @RequestParam int id) {
        return changeEmail.changeEmailSave(email,id);
    }
}
