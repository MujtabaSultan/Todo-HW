package com.ga.food.controller;

import com.ga.food.model.Category;
import com.ga.food.repository.CategoryRepository;
import com.ga.food.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping(path="/api")
public class CategoryController {
    //crud
    private CategoryService categoryService;

    @Autowired
    public void setCategoryService(CategoryService categoryService){
        this.categoryService=categoryService;
    }

    @PostMapping("/categories")
    public Category createCategory(@RequestBody Category categoryObject){
   Category created = categoryService.createCategory(categoryObject);
    return created;
    }
    @GetMapping("/categories")
    public ArrayList<Category> getCategroies(){
        return categoryService.getCategories();
    }
    @GetMapping("/categories/{id}")
    public Optional<Category> getCategory(@PathVariable Long id){
        return categoryService.getCategory(id);
    }
    @PutMapping("/categories")
    public Category updateCategory(@RequestBody Category categoryObj){
       return categoryService.updateCategory(categoryObj);
    }

    @PutMapping("/categories/{id}")
    public Category updateCategoryById(@RequestBody Category categoryObj,@PathVariable Long id){
        return categoryService.updateCategoryById(categoryObj,id);
    }
    @DeleteMapping("/categories/{id}")
    public Optional<Category> deleteCategory(@PathVariable Long id){
       return categoryService.deleteCategory(id);
    }




}
