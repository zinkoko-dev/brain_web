package com.group.brain_web.service.user;

import com.group.brain_web.models.User;
import com.group.brain_web.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    @Override
    public String editUser(int id, MultipartFile image, String userName) throws IOException {
        User user = userRepository.findById(id).get();
        user.setUserName(userName);
        byte[] photo = image.getBytes();
        String encodedPhoto = Base64.getEncoder().encodeToString(photo);
        user.setUserImage(encodedPhoto);
        userRepository.save(user);
        return "redirect:/?editUserSuccess";
    }
}
