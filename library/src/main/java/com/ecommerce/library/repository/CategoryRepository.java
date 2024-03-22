package com.ecommerce.library.repository;

import com.ecommerce.library.dto.CategoryDto;
import com.ecommerce.library.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAllByActivatedIsTrueAndDeletedIsFalse();
@Query("SELECT new com.ecommerce.library.dto.CategoryDto(c.id, c.name, COUNT(p.category.id)) FROM Category c INNER JOIN Product p ON c.id = p.category.id WHERE c.activated = true AND c.deleted = false GROUP BY c.id")
    List<CategoryDto> getCategoriesandProducts();
}
