package com.ga.food.service;

import com.ga.food.exception.InformationNotFoundException;
import com.ga.food.model.Category;
import com.ga.food.model.User;
import com.ga.food.repository.CategoryRepository;
import com.ga.food.security.MyUserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class CategoryService {
    CategoryRepository categoryRepository;
    public CategoryService(CategoryRepository categoryRepository){
        this.categoryRepository=categoryRepository;
    }
    public static User getCurrentLoggedInUser(){
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.getUser();
    }
    public Category createCategory(Category categoryObject){
        Category category = categoryRepository.findByUserIdAndName(CategoryService.getCurrentLoggedInUser().getId(), categoryObject.getName());
        if(category!=null){
            throw new RuntimeException("category with name "+category.getName()+" already exist");
        }
        else {

         //   System.out.println("ssssssssss");
           // System.out.println(categoryObject.getUser());
            Category created = new Category();
            created.setCreatedAt(LocalDateTime.now());
            created.setDescription(categoryObject.getDescription());
            created.setName(categoryObject.getName());
            created.setUpdatedAt(LocalDateTime.now());
            created.setUser(getCurrentLoggedInUser());
            return categoryRepository.save(created);
        }

    }

    public ArrayList<Category> getCategories() {
        ArrayList<Category> categories = new ArrayList<>(categoryRepository.findByUserId(getCurrentLoggedInUser().getId()));
        System.out.println(categories);
     //   System.out.println("ssssssssssssssssssssssssss");
        return categories;
    }

    public Optional<Category> getCategory(Long id) {
        if(categoryRepository.existsById(id)){
            return categoryRepository.findById(id);
        }
        else throw new InformationNotFoundException("category with id "+ id + " not found");
    }

    public Category updateCategory(Category categoryObj) {

        Category found = categoryRepository.findByName(categoryObj.getName());
        if(!getCurrentLoggedInUser().getId().equals(found.getUser().getId())) return null;

        found.setDescription(categoryObj.getDescription());
       return categoryRepository.save(found);
    }
    public Optional<Category> deleteCategory(Long id) {
        Category found = categoryRepository.findById(id).orElse(null);
        System.out.println(found.getUser().getId().equals(getCurrentLoggedInUser().getId()));
        System.out.println("dddddddddddddddd");
        System.out.println("toekn id is " + getCurrentLoggedInUser().getId());
        System.out.println("user id is " +found.getUser().getId() );
        if(!getCurrentLoggedInUser().getId().equals(found.getUser().getId())) return null;
        System.out.println("wrooooooooong");

        if(categoryRepository.existsById(id)){
            Category deleted = categoryRepository.findById(id).orElse(null);
            categoryRepository.deleteById(id);
            return Optional.of(deleted);
        }
        else throw new InformationNotFoundException("the given id doesnt exist to even delete it ");
    }

    public Category updateCategoryById(Category categoryObj,Long id) {
        Category found = categoryRepository.findById(id).orElse(null);
        found.setDescription(categoryObj.getDescription());
        found.setUpdatedAt(LocalDateTime.now());
        return categoryRepository.save(found);

    }
}
