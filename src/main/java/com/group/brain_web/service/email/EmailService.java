package com.group.brain_web.service.email;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.UnsupportedEncodingException;

public interface EmailService {

    void send(String to, String email);

    String emailForm(String name, String link);

    String contact(HttpServletRequest request, MultipartFile multipartFile) throws MessagingException, UnsupportedEncodingException;

    void changeEmail(String email, Integer id) throws MessagingException, UnsupportedEncodingException;

}
