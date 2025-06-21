package com.projsalony.service;

import com.projsalony.dto.SalonDTO;
import com.projsalony.model.Category;

import java.util.List;
import java.util.Set;

public interface CategoryService {

    Category createCategory(Category category, SalonDTO salonDTO);

    Set<Category> getAllCategoryBySalon(Long id);

    Category getCategoryById(Long id) throws Exception;

    void deleteCategoryById(Long id, Long salonId) throws Exception;

    List<Category> getAllCategories();

    Category updateCategory(Long id, Category category) throws Exception;
}
