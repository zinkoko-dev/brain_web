package com.group.brain_web.service.password.reset;

import com.group.brain_web.models.User;
import com.group.brain_web.repository.UserRepository;
import com.group.brain_web.service.email.EmailService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PasswordResetServiceImpl implements PasswordResetService {

    private final UserRepository userRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public String passwordReset(String email) {

        /*User user = userRepository.resetPasswordByEmail(email);

        if(user!=null) {
            String link = "http://localhost:8080/password/reset/save/"+ user.getUserId();
            emailService.send(user.getEmail(),emailService.emailForm(user.getUserName(),link));
            return "redirect:/?CheckYourEmail";
        }else {
            return "redirect:/password/reset/form?error";
        }*/

        User user = userRepository.resetPasswordByEmail(email);

        if(user!=null) {
            String link = "http://localhost:8080/password/reset/save/"+ user.getUserId();
            emailService.send(user.getEmail(),emailService.emailForm(user.getUserName(),link));
            return "redirect:/?CheckYourEmail";
        }else {
            return "redirect:/password/reset/form?error";
        }
    }

    @Override
    public String passwordResetConfirm(String password, int id) {
        User user = userRepository.findById(id).get();
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
        return "redirect:/?saveChange";
    }

}
