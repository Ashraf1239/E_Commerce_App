package com.ecommerce.library.service;

import com.ecommerce.library.dto.CategoryDto;
import com.ecommerce.library.model.Category;
import com.ecommerce.library.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> findAllcategory() {

        return categoryRepository.findAll();
    }

    public Category savecategory(Category category) {
        try {
            Category savedCategory = new Category(category.getName());
            return categoryRepository.save(savedCategory);
        }
        catch ( Exception e) {
            e.printStackTrace();
            return null;
        }


    }

    public Category getcategorybyid(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));
    }

    public Category updatecategory(Category category) {
        Category updateCategory1 = categoryRepository.getReferenceById(category.getId());
        updateCategory1.setName(category.getName());
        return categoryRepository.save(updateCategory1);
    }

    public void deletecategory(Long id) {
        Category deleteCategory = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));
        deleteCategory.setActivated(false);
        deleteCategory.setDeleted(true);
        categoryRepository.save(deleteCategory);
    }
    public void enableById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));
        category.setActivated(true);
        category.setDeleted(false);
        categoryRepository.save(category);
    }

    public List<Category>findCategoriesbyActive(){
        return  categoryRepository.findAllByActivatedIsTrueAndDeletedIsFalse();
    }
public List<CategoryDto> getCategoriesandProducts(){
    return categoryRepository.getCategoriesandProducts();
}


}
