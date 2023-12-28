package com.group.brain_web.controller;

import com.group.brain_web.dto.CategoryDto;
import com.group.brain_web.models.Category;
import com.group.brain_web.models.Course;
import com.group.brain_web.models.Role;
import com.group.brain_web.models.User;
import com.group.brain_web.repository.CategoryRepository;
import com.group.brain_web.repository.CourseRepository;
import com.group.brain_web.repository.UserRepository;
import com.group.brain_web.service.admin.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@AllArgsConstructor
@Controller
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final CourseRepository courseRepository;

    @GetMapping("/dashboard")
    public String home(Model model) {

        List<User> users = userRepository.findAll();
        List<Category> categories = categoryRepository.findAll();
        List<Course> courses = courseRepository.findAll();
        model.addAttribute("users", users);
        model.addAttribute("categories", categories);
        model.addAttribute("courses", courses);

        return "admin/dashboard";
    }

    @GetMapping("/user/view")
    public String userView(Model model) {
        return adminService.userView(model);
    }

    @GetMapping("/user/edit")
    public String userEdit(Model model, int id) {
        return adminService.userEdit(model, id);
    }

    @PostMapping("/user/edit")
    public String saveEditUser(@RequestParam("id") int id,
                               @RequestParam("userName") String userName,
                               @RequestParam("role") Role role,
                               @RequestParam("status") String status) throws IOException {
        return adminService.saveEditUser(id, userName, role, status);
    }

    @GetMapping("/user/disable")
    public String userDisable(@RequestParam("id") int id) {
        userRepository.disableUserByAdmin(id);
        return "redirect:/admin/user/view";
    }

    @GetMapping("/user/enable")
    public String userEnable(@RequestParam("id") int id) {
        userRepository.enableUserByAdmin(id);
        return "redirect:/admin/user/view";
    }

    @GetMapping("/add/category")
    public String addCategory() {
        return "admin/add_category";
    }

    @PostMapping("/add/category")
    public String addCategoryPost(@RequestParam("categoryName") String categoryName,
                                  @RequestParam("description") String description,
                                  @RequestParam("categoryImage") MultipartFile image) throws IOException {
        return adminService.addCategory(categoryName,image,description);
    }

    @GetMapping("/add/course")
    public String addCourse(Model model){
        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);
        return "admin/add_course";}

    @PostMapping("/add/course")
    public String addCoursePost(@RequestParam("courseName")String courseName,
                                @RequestParam("category")String category,
                                @RequestParam("courseLink")String courseLink,
                                @RequestParam("plan")String plan) throws IOException {

        return adminService.addCourse(courseName, category, courseLink, plan);
    }
    @GetMapping("directAdd/course")
    public String directAddCourse(Model model, @RequestParam("id") int id){
        Category category = categoryRepository.findById(id).orElse(null);
        model.addAttribute("category",category);
        return "admin/directAdd_course";
    }
    @PostMapping("/directAdd/course")
    public String directAddCoursePost(@RequestParam("courseName")String courseName,
                                      @RequestParam("category")String category,
                                      @RequestParam("courseLink")String courseLink,
                                      @RequestParam("plan")String plan) {
        return adminService.directAddCourse(courseName, category, courseLink, plan);
    }
    @GetMapping("/list")
    public String courseList(Model model, @RequestParam("id") int id){
        List<Course> course = courseRepository.findByCategoryId(id);
        Category category = categoryRepository.findById(id).orElse(null);
        model.addAttribute("category", category);
        model.addAttribute("course", course);
        return "admin/course_list";}

    @GetMapping("/edit/course")
    public String editCourse(@RequestParam("id")int id,@RequestParam("catId")int catId, Model model){
        Course courses = courseRepository.findById(id).orElse(null);
        model.addAttribute("course", courses);
        Category category = categoryRepository.findById(catId).orElse(null);
        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("category",category);
        model.addAttribute("categories", categories);
        return "admin/edit_course";
    }

    @PostMapping("/edit/course")
    public String editCoursePost(@RequestParam("id")int id,
                                 @RequestParam("courseName")String name,
                                 @RequestParam("category") String category,
                                 @RequestParam("courseLink")String courseLink,
                                 @RequestParam("plan")String coursePlan){
        adminService.editCourse(id, name, category, courseLink, coursePlan);
        return "redirect:/admin/dashboard?editCourseSuccess";
    }

    @GetMapping("/delete/course")
    public String deleteCourse(@RequestParam("id")int id){
        courseRepository.deleteById(id);
        return "redirect:/admin/list?deleteCourseSuccess";
    }

    @GetMapping("/search")
    public String searchUser(@RequestParam("term")String searchTerm, Model model){
        List<User> users = userRepository.search(searchTerm);
        model.addAttribute("users", users);
        return "admin/user_search";
    }

    @GetMapping("/update/category")
    public String updatePlayList(Model model){
        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);
        return "admin/update_categorylist";
    }

    @GetMapping("/edit/category")
    public String editPlaylist(@RequestParam("id")int id, Model model) throws IOException {
        Category category = categoryRepository.findById(id).orElse(null);
        byte[] photo = category.getCategoryImage().getBytes();
        Base64.getDecoder().decode(photo);
        model.addAttribute("editCategory", category);
        return "admin/edit_category";
    }

    @PostMapping("/edit/category")
    public String editPlaylistPost(@RequestParam("id")int id,
                                   @RequestParam("image")MultipartFile file,
                                   @RequestParam("status")String status,
                                   @RequestParam("title")String title) throws IOException {
        return   adminService.editCategory(id, file, status, title);

    }

    @GetMapping("/delete/category")
    public String deletePlaylist(@RequestParam("id")int id){
        categoryRepository.deleteById(id);
        return "redirect:/admin/update/category";
    }
}


