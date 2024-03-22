package com.ecommerce.customers.controller;

import com.ecommerce.library.dto.CategoryDto;
import com.ecommerce.library.dto.ProductDto;
import com.ecommerce.library.model.Category;
import com.ecommerce.library.model.Product;
import com.ecommerce.library.service.CategoryService;
import com.ecommerce.library.service.ProductService;
import com.ecommerce.library.utils.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
    @GetMapping("/menu")
    public String index(Model model){
        List<ProductDto> productDtos=productService.allProducts();
        List <Category> categories=categoryService.findAllcategory();
        model.addAttribute("categories",categories);
        model.addAttribute("imgUtil", new ImageUtil());
        model.addAttribute("products",productDtos);
        return "index";
    }
    @GetMapping("/products")
    public String getProducts(Model model) {
        List<CategoryDto> categories = categoryService.getCategoriesandProducts();
        List<Product> products = productService.getAllProducts();
        List<Product> products1 = productService.ListViewProducts();
        model.addAttribute("categories", categories);
        model.addAttribute("productViews", products1);
        model.addAttribute("products", products);
        model.addAttribute("imgUtil", new ImageUtil());

        return "products";

    }

    @GetMapping("/product-detail/{id}")
    public String getProductById(@PathVariable("id") Long id, Model model) {
        Product product = productService.getProductById2(id);
        Long categoryId = product.getCategory().getId();
        List<Product> products = productService.getRelatedProducts(categoryId);
        model.addAttribute("imgUtil", new ImageUtil());
        model.addAttribute("productDetail", product);
        model.addAttribute("products", products);
        return "product-detail";
    }
    @GetMapping("/find-products/id")
    public String getProductsByCategory(@PathVariable Long categoryId, Model model) {
        List<CategoryDto> categories = categoryService.getCategoriesandProducts();
        List<Product> products = productService.getProductinCategory(categoryId);
        List<Product> products1 = productService.ListViewProducts();
        model.addAttribute("categories", categories);
        model.addAttribute("productViews", products1);
        model.addAttribute("products", products);
        model.addAttribute("imgUtil", new ImageUtil());
        return "products";
    }
    @GetMapping("/high-price")
    public String filterHighPrice(Model model) {
        List<CategoryDto> categories = categoryService.getCategoriesandProducts();
        model.addAttribute("categories", categories);
        List<ProductDto> products = productService.getFilterByUpper();
        List<Product> listView = productService.ListViewProducts();
        model.addAttribute("title", "Shop Detail");
        model.addAttribute("page", "Shop Detail");
        model.addAttribute("productViews", listView);
        model.addAttribute("products", products);
        model.addAttribute("imgUtil", new ImageUtil());
        return "shop-detail";
    }
    @GetMapping("/lower-price")
    public String filterLowerPrice(Model model) {
        List<CategoryDto> categories = categoryService.getCategoriesandProducts();
        model.addAttribute("categories", categories);
        List<ProductDto> products = productService.getFilterByLower();
        List<Product> listView = productService.ListViewProducts();
        model.addAttribute("title", "Shop Detail");
        model.addAttribute("page", "Shop Detail");
        model.addAttribute("productViews", listView);
        model.addAttribute("products", products);
        model.addAttribute("imgUtil", new ImageUtil());
        return "shop-detail";
    }


}
