package com.group.brain_web.controller;

import com.group.brain_web.models.User;
import com.group.brain_web.repository.UserRepository;
import com.group.brain_web.service.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@Controller
@AllArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;

    @GetMapping("/user/edit")
    public String editUser(@RequestParam("id")int id, Model model)throws IOException{

        User user = userRepository.findById(id).orElse(null);

        byte[] photo = user.getUserImage().getBytes();
        Base64.getDecoder().decode(photo);
        model.addAttribute("user", user);

        return "user/user_edit";
    }

    @PostMapping("/user/edit")
    public String editUserPost(@RequestParam("id")int id,
                               @RequestParam("image") MultipartFile image,
                               @RequestParam("userName") String userName) throws IOException {
        return userService.editUser(id, image, userName);
    }
}
