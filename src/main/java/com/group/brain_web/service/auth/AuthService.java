package com.group.brain_web.service.auth;

import com.group.brain_web.dto.RegisterDto;
import jakarta.mail.MessagingException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface AuthService {
    String register(RegisterDto registerDto, MultipartFile image) throws IOException, MessagingException;

    String confirmToken(String token);
}
