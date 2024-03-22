package com.ecommerce.customers.controller;

import com.ecommerce.library.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

}
