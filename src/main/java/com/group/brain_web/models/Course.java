package com.group.brain_web.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int courseId;
    private String courseName;
    private String courseLink;
    private String coursePlan;

//    private String courseVideoTitle;
//    @Lob
//    @Column(columnDefinition = "longblob")
//    private String courseImage;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "category_id")
    private Category category;

    public Course(String courseName, String courseLink, Category category) {
        this.courseName = courseName;
        this.courseLink = courseLink;
        this.category = category;
    }
}
