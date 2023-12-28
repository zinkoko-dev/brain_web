package com.group.brain_web.controller;

import com.group.brain_web.config.MyUserDetail;
import com.group.brain_web.models.Category;
import com.group.brain_web.models.Course;
import com.group.brain_web.models.User;
import com.group.brain_web.repository.CategoryRepository;
import com.group.brain_web.repository.CourseRepository;
import com.group.brain_web.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/course")
public class CourseController {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @GetMapping("/list")
    public String courseList(Model model, int id){

        List<Course> course = courseRepository.findByCategoryId(id);
        Category category = categoryRepository.findById(id).orElse(null);


        /*Get UserId from Spring Security's context holder*/
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetail myUserDetail = (MyUserDetail) authentication.getPrincipal();

        User user = userRepository.findById(myUserDetail.getUserId()).orElse(null);

        model.addAttribute("category", category);
        model.addAttribute("user", user);
        model.addAttribute("course", course);
        return "course/list";
    }

    @GetMapping("/search")
    public String courseSearch(@RequestParam("term")String searchTerm, Model model){
        List<Course> courses = courseRepository.search(searchTerm);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetail myUserDetail = (MyUserDetail) authentication.getPrincipal();

        User user = userRepository.findById(myUserDetail.getUserId()).orElse(null);
        model.addAttribute("user", user);
        model.addAttribute("courses", courses);
        return "course/courseSearch";
    }
}
