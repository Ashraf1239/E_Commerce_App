package com.ecommerce.library.repository;

import com.ecommerce.library.model.Product;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p")
    Page<Product> pageProducts(Pageable pageable);
    @Query("Select p from Product p where p.description like %?1% or p.name like %?1%")
    Page <Product>  searchProduct(String name, Pageable pageable);

    @Query("select p from Product p where p.is_actived =true and p.is_deleted=false ")
    List<Product> getAllProducts();
    @Query("SELECT p FROM Product p WHERE p.is_actived = true AND p.is_deleted = false ORDER BY random() ASC LIMIT 4")
    List<Product> ListViewProducts();
    @Query("SELECT p FROM Product p WHERE p.category.id = ?1 and  p.is_actived = true AND p.is_deleted = false")
        List<Product> getRelatedProducts(Long categoryId);
@Query("select c from Product c where c.category.id = ?1 and c.is_actived = true and c.is_deleted = false")
    List<Product>getProductinCategory(Long categoryId);
@Query("select p from Product p where p.is_actived=true and p.is_deleted=false order by p.costPrice asc limit 9")
List<Product> filterByLower();

    @Query("select p from Product p where p.is_actived=true and p.is_deleted=false order by p.costPrice desc limit 9")
    List<Product> filterByHigher();
}
