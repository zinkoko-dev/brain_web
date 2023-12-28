package com.group.brain_web.repository;

import com.group.brain_web.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Integer> {

    @Query("select r from Category r where r.categoryName=?1")
    Category findByName(String categoryName);
}
