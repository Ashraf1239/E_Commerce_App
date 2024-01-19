package com.ecommerce.library.repository;

import com.ecommerce.library.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RuleRepository extends JpaRepository<Role,Long> {
    Role findByName(String name);
}
