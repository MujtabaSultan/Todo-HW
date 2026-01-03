package com.ga.food.repository;

import com.ga.food.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
    Category findByName(String categotyName);
    Category findByIdAndUserId(Long CategoryId , Long userId);
    List<Category> findByUserId(Long userId);
    Category findByUserIdAndName(Long userId,String categoryName);
}
