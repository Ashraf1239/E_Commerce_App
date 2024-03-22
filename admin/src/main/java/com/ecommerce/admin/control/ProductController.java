package com.ecommerce.admin.control;

import com.ecommerce.library.dto.ProductDto;
import com.ecommerce.library.model.Category;
import com.ecommerce.library.model.Product;
import com.ecommerce.library.repository.ProductRepository;
import com.ecommerce.library.service.CategoryService;
import com.ecommerce.library.service.ProductService;
import com.ecommerce.library.utils.ImageUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.security.Principal;
import java.util.List;


@Controller
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
    @GetMapping("/products")
    public  String products(Model model , Principal principal){
        if(principal == null){
            return "redirect:/login";
        }
        List<ProductDto> products = productService.allProducts();
        model.addAttribute("products",products);
        model.addAttribute("size",products.size());
        model.addAttribute("productNew",new Product());
        model.addAttribute("imgUtil", new ImageUtil());
        model.addAttribute("title","Products");
        return "products";

    }
  @PostMapping("/save-product")
    public String saveProduct(@ModelAttribute("productDto") ProductDto product,
                             Model model,
                             @RequestParam("imageProduct") MultipartFile imageProduct,
                             RedirectAttributes redirectAttributes,
                             HttpServletRequest request
                             ) {


            productService.saveProduct(product, imageProduct);
      model.addAttribute("imgUtil", new ImageUtil());
            redirectAttributes.addFlashAttribute("success", " Add Product successfully");
            return "redirect:/products/0"; // Redirect after successful form submission

    }
    @GetMapping("/addproduct")
    public String addProduct(Long id,
                              Model model,
                              RedirectAttributes redirectAttributes) {
        List<Category> categories = categoryService.findCategoriesbyActive();
        model.addAttribute("categories", categories);
        model.addAttribute("productDto", new ProductDto());
        // This block will be executed for GET requests
        return "addProduct";

    }

  @RequestMapping(value = "/update-form", method = {RequestMethod.PUT, RequestMethod.GET})
    public String updateProduct_Form( Long id, Model model) {
        ProductDto productDto = productService.getProductById(id);
        List<Category> categories = categoryService.findCategoriesbyActive();

        model.addAttribute("productDto", productDto);
        model.addAttribute("categories", categories);
        model.addAttribute("title", "Update Product");
      model.addAttribute("imgUtil", new ImageUtil());



        return "update-product";
    }
    @PostMapping("/update_product/{id}")
    public String updateProduct(@PathVariable("id") Long id,@ModelAttribute("productDto") ProductDto productDto, Model model, @RequestParam("imageProduct") MultipartFile imageProduct,
                                RedirectAttributes redirectAttributes) throws IOException {
        System.out.println("welcome here");
        productService.updateProduct(productDto, imageProduct, id);

        model.addAttribute("imgUtil", new ImageUtil());
        redirectAttributes.addFlashAttribute("success", " Update Product successfully");
        return "redirect:/products/0";
    }
    @RequestMapping(value = "/delete-product", method = {RequestMethod.DELETE, RequestMethod.GET})
    public String deleteProduct(Long id, RedirectAttributes redirectAttributes) {
        productService.deleteProduct(id);
        redirectAttributes.addFlashAttribute("success", " Delete Product successfully");
        return "redirect:/products/0";
    }
    @RequestMapping(value = "/active-product", method = {RequestMethod.PUT, RequestMethod.GET})
    public String activeProduct(Long id, RedirectAttributes redirectAttributes) {
        productService.enableProduct(id);
        redirectAttributes.addFlashAttribute("success", " Active Product successfully");
        return "redirect:/products/0";
    }
    @GetMapping("/products/{page}")
    public String getProductsByPage(@PathVariable("page") int page, Model model,Principal principal) {
        if(principal == null){
            return "redirect:/login";
        }
        Page<Product> products = productService.pageProducts(page);
        model.addAttribute("products",products);
        model.addAttribute("size",products.getSize());
        model.addAttribute("imgUtil", new ImageUtil());
        model.addAttribute("totalPages",products.getTotalPages());
        model.addAttribute("currentPage",page);


        model.addAttribute("title","Manage Product");
        return "products";

    }
    @GetMapping("/searchproduct/{page}")
    public String searchProduct(@RequestParam("keyword") String keyword, @PathVariable("page") int page, Model model,Principal principal) {
        if(principal == null){
            return "redirect:/login";
        }
        Page<Product> products = productService.searchProduct(keyword,page);
        model.addAttribute("products",products);
        model.addAttribute("imgUtil", new ImageUtil());
        model.addAttribute("size",products.getSize());
        model.addAttribute("totalPages",products.getTotalPages());
        model.addAttribute("currentPage",page);
        model.addAttribute("title","Search Product");
        return "products";
    }



}
