package com.group.brain_web.service.user;

import com.group.brain_web.models.User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


public interface UserService {
    String editUser(int id, MultipartFile image, String userName)throws IOException;
}
