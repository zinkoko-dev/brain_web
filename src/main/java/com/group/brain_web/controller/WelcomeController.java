package com.group.brain_web.controller;

import com.group.brain_web.models.Category;
import com.group.brain_web.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Base64;
import java.util.List;

@Controller
@AllArgsConstructor
public class WelcomeController {

    private final CategoryRepository categoryRepository;

    @GetMapping("/")
    public String welcomePage(Model model) {

        List<Category> categories = categoryRepository.findAll();
        for (Category category: categories){
            byte[] photo = category.getCategoryImage().getBytes();
            Base64.getDecoder().decode(photo);
        }
        model.addAttribute("categories", categories);

        return "page/welcome";
    }

    @GetMapping("/about")
    public String about() {
        return "page/about";
    }

}
