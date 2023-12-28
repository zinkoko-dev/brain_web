package com.group.brain_web.controller;

import com.group.brain_web.dto.RegisterDto;
import com.group.brain_web.service.auth.AuthService;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("register", new RegisterDto());
        return "auth/register";
    }

    @PostMapping("/register")
    public String registerPost(RegisterDto registerDto,
                               @RequestParam("image") MultipartFile image) throws IOException, MessagingException {
        return authService.register(registerDto, image);
    }

    @GetMapping("/confirm")
    public String confirm(@RequestParam("token") String token){
        return authService.confirmToken(token);
    }

}
