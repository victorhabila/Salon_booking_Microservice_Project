package com.projsalony.controller;

import com.projsalony.dto.SalonDTO;
import com.projsalony.model.Category;
import com.projsalony.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // Get all Categories
    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping("/salon/{id}")
    public ResponseEntity<Set<Category>> getCategoriesBySalon(@PathVariable Long id){
        Set<Category> categories = categoryService.getAllCategoryBySalon(id);
        return ResponseEntity.ok(categories);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) throws Exception {
        Category category = categoryService.getCategoryById(id);
        return ResponseEntity.ok(category);

    }
    @PatchMapping("/{id}")
    public ResponseEntity<Category> updateCategory(
            @PathVariable Long id,
            @RequestBody Category category
    ) throws Exception {
        Category updatedCategory = categoryService.updateCategory(id,category);
        return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id) throws Exception {
        SalonDTO salonDTO = new SalonDTO();// THIS WILL BE CORRECTED AFTER I HAVE SET UP KEYCLOAK AUTHENTICATION
        salonDTO.setId(1L);
        categoryService.deleteCategoryById(id,salonDTO.getId());
        return ResponseEntity.ok("Category deleted successfully");
    }

}
