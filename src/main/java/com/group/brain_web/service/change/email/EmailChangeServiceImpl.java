package com.group.brain_web.service.change.email;

import com.group.brain_web.models.User;
import com.group.brain_web.repository.UserRepository;
import com.group.brain_web.service.email.EmailService;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
@AllArgsConstructor
public class EmailChangeServiceImpl implements EmailChangeService {

    private final UserRepository userRepository;
    private final EmailService emailService;

    @Override
    public String changeEmail(String email) throws UnsupportedEncodingException, MessagingException {

        User user = userRepository.resetPasswordByEmail(email);

        if(user!=null) {

            emailService.changeEmail(email, user.getUserId());

            return "redirect:/?CheckYourEmail";

        }else {

            return "redirect:/email/change?error";
        }
    }

    @Override
    public String changeEmailSave(String email, int id) {

        if ( userRepository.existsUserByEmail(email) ) {
            return "redirect:/?emailAlreadyTaken";
        }
        User user = userRepository.findById(id).get();
        user.setEmail(email);
        userRepository.save(user);
        return "redirect:/?emailChangeSuccess";
    }
}
