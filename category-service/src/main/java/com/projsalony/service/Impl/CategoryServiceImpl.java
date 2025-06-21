package com.projsalony.service.Impl;

import com.projsalony.dto.SalonDTO;
import com.projsalony.model.Category;
import com.projsalony.repository.CategoryRepository;
import com.projsalony.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category createCategory(Category category, SalonDTO salonDTO) {
        Category newCategory = new Category();
        newCategory.setName(category.getName());
        newCategory.setImage(category.getImage());
        newCategory.setSalonId(salonDTO.getId());

        return categoryRepository.save(newCategory);
    }

    @Override
    public Set<Category> getAllCategoryBySalon(Long id) {

        return categoryRepository.findBySalonId(id);
    }

    @Override
    public Category getCategoryById(Long id) throws Exception {
        Category category = categoryRepository.findById(id).orElse(null);
        if(category == null){
            throw new Exception("Category not exist with id "+ id);
        }

        return category;
    }
    @Override
    public void deleteCategoryById(Long id, Long salonId) throws Exception {
        Category category = getCategoryById(id);

        if(!category.getSalonId().equals(salonId)){ // this will allow users to delete only there category and not the category of others
            throw new Exception("You don't have permission to delete this category");
        }
        categoryRepository.deleteById(id);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category updateCategory(Long id, Category category) throws Exception {
        Category existingCategory = getCategoryById(id);
        if(category.getName()!=null){
            existingCategory.setName(category.getName());
        }
        if(category.getImage()!= null){
            existingCategory.setImage(category.getImage());
        }
        return categoryRepository.save(existingCategory);
    }
}
