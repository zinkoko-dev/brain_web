package com.group.brain_web.service.admin;

import com.group.brain_web.dto.CategoryDto;
import com.group.brain_web.models.Category;
import com.group.brain_web.models.Course;
import com.group.brain_web.models.Role;
import com.group.brain_web.models.User;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface AdminService {

    String userView(Model model);

    String userEdit(Model model, int id);

    String saveEditUser(int id, String userName, Role role, String status) throws IOException;

    String addCategory(String categoryName, MultipartFile image, String description) throws IOException;

    String addCourse(String courseName, String category, String courseLink, String plan);

    String editCategory(int id, MultipartFile categoryImage, String categoryName, String status) throws IOException;

    Course editCourse(int id, String courseName, String category, String courseLink, String plan);

    String directAddCourse(String courseName, String category, String courseLink, String plan);

}