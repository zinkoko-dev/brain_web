package com.group.brain_web.repository;

import com.group.brain_web.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {


    @Query("SELECT c FROM Course c WHERE c.category.id = :id")
    List<Course> findByCategoryId(@Param("id") int id);

    @Query("SELECT c FROM Course c WHERE c.courseName LIKE %:searchTerm%")
    List<Course> search(@Param("searchTerm") String searchTerm);

}
