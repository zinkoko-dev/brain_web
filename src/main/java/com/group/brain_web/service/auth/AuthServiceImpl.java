package com.group.brain_web.service.auth;

import com.group.brain_web.dto.RegisterDto;
import com.group.brain_web.models.Role;
import com.group.brain_web.models.Token;
import com.group.brain_web.models.User;
import com.group.brain_web.repository.TokenRepository;
import com.group.brain_web.repository.UserRepository;
import com.group.brain_web.service.email.EmailService;
import com.group.brain_web.service.email.EmailValidator;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final PasswordEncoder passwordEncoder;
    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final EmailValidator emailValidator;

    @Override
    public String register(RegisterDto registerDto, MultipartFile image) throws IOException{

        if(!emailValidator.validate(registerDto.getEmail())) {
            return "redirect:/register?InvalidEmail";
        }

        if(userRepository.existsUserByEmail(registerDto.getEmail())) {
            return "redirect:/?EmailAlreadyTaken";
        }

        User user = new User();

        user.setUserName(registerDto.getUserName());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        byte[] photo = image.getBytes();
        String encodedPhoto=  Base64.getEncoder().encodeToString(photo);
        user.setUserImage(encodedPhoto);
        user.setRole(Role.USER);
        userRepository.save(user);

        //Create Token
        String getToken = UUID.randomUUID().toString();
        Token token = new Token();
        token.setToken(getToken);
        token.setCreateAt(LocalDateTime.now());
        token.setExpiredAt(LocalDateTime.now().plusMinutes(2));
        token.setUser(user);

        tokenRepository.save(token);

        //Send Verification Email
        String link = "http://localhost:8080/confirm?token="+ getToken;
        emailService.send(
                registerDto.getEmail(),
                emailService.emailForm(registerDto.getUserName(), link)
        );

        return "redirect:/?confirm";
    }

    @Override
    public String confirmToken(String token) {

        Token confirmToken = tokenRepository.findByToken(token);
        LocalDateTime expiredAt = confirmToken.getExpiredAt();
        if (confirmToken == null) {
            return "redirect:/?Token Not Found";
        }
        else if (confirmToken.getConfirmedAt() != null) {
            return "redirect:/?Email already Confirm";
        }
        else if (expiredAt.isBefore(LocalDateTime.now())) {
            return "redirect:/?Token Expired";
        }else {
            tokenRepository.updateConfirmedAt(token, LocalDateTime.now());
            userRepository.enableUser(confirmToken.getUser().getEmail());
            return "redirect:/?success";
        }

    }
}
