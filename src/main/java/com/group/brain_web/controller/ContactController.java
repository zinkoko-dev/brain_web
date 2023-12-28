package com.group.brain_web.controller;

import com.group.brain_web.service.email.EmailService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.UnsupportedEncodingException;

@Controller
@AllArgsConstructor
public class ContactController {

    private final EmailService emailService;

    @GetMapping("/contact")
    public String contactForm() {
        return "page/contact";
    }

    @PostMapping("/contact")
    public String contactPost(HttpServletRequest request,
                              @RequestParam("attachment") MultipartFile multipartFile)
            throws UnsupportedEncodingException, MessagingException {

        return emailService.contact(request,multipartFile);
    }

}
