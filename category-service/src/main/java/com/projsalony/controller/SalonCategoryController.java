package com.projsalony.controller;

import com.projsalony.dto.SalonDTO;
import com.projsalony.model.Category;
import com.projsalony.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/salon-owner")
public class SalonCategoryController {

    private final CategoryService categoryService;

    public SalonCategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody Category category){

        SalonDTO salonDTO = new SalonDTO();// THIS WILL BE CORRECTED AFTER I HAVE SET UP KEYCLOAK AUTHENTICATION
        salonDTO.setId(1L);
        Category newCategory = categoryService.createCategory(category,salonDTO );
        return ResponseEntity.ok(newCategory);
    }


}
