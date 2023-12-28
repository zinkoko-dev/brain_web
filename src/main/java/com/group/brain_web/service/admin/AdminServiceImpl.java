package com.group.brain_web.service.admin;

import com.group.brain_web.dto.CategoryDto;
import com.group.brain_web.models.Category;
import com.group.brain_web.models.Course;
import com.group.brain_web.models.Role;
import com.group.brain_web.models.User;
import com.group.brain_web.repository.CategoryRepository;
import com.group.brain_web.repository.CourseRepository;
import com.group.brain_web.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Service
@AllArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final CourseRepository courseRepository;

    @Override
    public String userView(Model model) {

        List<User> userList = userRepository.findAll();
        for( User user : userList ) {
            byte[] getPhoto = user.getUserImage().getBytes();
            Base64.getDecoder().decode(getPhoto);
        }
        model.addAttribute("users", userList);
        return "admin/user_view";
    }

    @Override
    public String userEdit(Model model, int id) {

        User user = userRepository.findById(id).get();

        /*byte[] getPhoto = user.getUserImage().getBytes();
        Base64.getDecoder().decode(getPhoto);*/

        model.addAttribute("edit",user);

        return "admin/user_edit";
    }

    @Override
    public String saveEditUser(int id, String userName, Role role, String status) throws IOException {

        User user = userRepository.findById(id).get();
        user.setUserName(userName);
        user.setRole(role);
        user.setStatus(status);

        /*byte[] photo = image.getBytes();
        String encodedPhoto = Base64.getEncoder().encodeToString(photo);
        user.setUserImage(encodedPhoto);*/

        userRepository.save(user);

        return "redirect:/admin/user/view";

    }

    @Override
    public String addCategory(String categoryName, MultipartFile image, String description) throws IOException {

        Category category = new Category();

        category.setCategoryName(categoryName);
        category.setDescription(description);

        byte[] photo = image.getBytes();
        String encodePhoto = Base64.getEncoder().encodeToString(photo);
        category.setCategoryImage(encodePhoto);

        categoryRepository.save(category);

        return "redirect:/admin/dashboard?successCategoryAdd";

    }

    @Override
    public String addCourse(String courseName, String category, String courseLink, String plan) {
        Course course = new Course();
        Category category1 = categoryRepository.findByName(category);
        course.setCourseName(courseName);
        course.setCourseLink(courseLink);
        course.setCategory(category1);
        course.setCoursePlan(plan);
        courseRepository.save(course);
        return "redirect:/admin/dashboard?successCourseAdd";
    }

    @Override
    public String editCategory(int id, MultipartFile categoryImage, String status, String categoryName) throws IOException {
        Category category = categoryRepository.findById(id).get();
        category.setStatus(status);
        category.setCategoryName(categoryName);
        if(!categoryImage.isEmpty()){
        byte[] photo = categoryImage.getBytes();
        String encodedPhoto = Base64.getEncoder().encodeToString(photo);
        category.setCategoryImage(encodedPhoto);
        }
         categoryRepository.save(category);
         return "redirect:/admin/update/category";
    }

    @Override
    public Course editCourse(int id, String courseName, String category, String courseLink, String plan) {
        Course course = courseRepository.findById(id).get();
        course.setCourseName(courseName);
        course.setCategory(categoryRepository.findByName(category));
        course.setCourseLink(courseLink);
        course.setCoursePlan(plan);
        return courseRepository.save(course);
    }

    @Override
    public String directAddCourse(String courseName, String category, String courseLink, String plan) {
        Course course = new Course();
        course.setCourseName(courseName);
        course.setCategory(categoryRepository.findByName(category));
        course.setCourseLink(courseLink);
        course.setCoursePlan(plan);
        courseRepository.save(course);
        return "redirect:/admin/update/category?directAddCourseSuccess";
    }

}
