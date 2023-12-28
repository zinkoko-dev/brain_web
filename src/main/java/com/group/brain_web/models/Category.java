package com.group.brain_web.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String categoryName;
    private String status;
    private String description;

    @Lob
    @Column(columnDefinition = "longBlob")
    private String categoryImage;

    @OneToMany(mappedBy = "category",cascade = CascadeType.ALL)
    private List<Course> courses= new ArrayList<>();

    public Category(String categoryName, String status, String description, String categoryImage, List<Course> courses) {
        this.categoryName = categoryName;
        this.status = status;
        this.description = description;
        this.categoryImage = categoryImage;
        this.courses = courses;
    }
}
