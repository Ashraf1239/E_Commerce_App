package com.ecommerce.admin.control;

import com.ecommerce.library.model.Category;
import com.ecommerce.library.repository.CategoryRepository;
import com.ecommerce.library.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/categories")
    public String categories(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }


        List<Category> categories = categoryService.findAllcategory();
        model.addAttribute("categories", categories);
        model.addAttribute("size", categories.size());
        model.addAttribute("categoryNew", new Category());
        model.addAttribute("title", "Categories");
        return "categories";
    }
    @RequestMapping(value = "/findById", method = {RequestMethod.PUT, RequestMethod.GET})
    @ResponseBody
    public Optional<Category> findById(Long id) {
        return Optional.ofNullable(categoryService.getcategorybyid(id));
    }

    @PostMapping("/add-category")
    public String saveCategory(@ModelAttribute("categoryNew") Category category, Model model, RedirectAttributes redirectAttributes) {
        try {

            categoryService.savecategory(category);
            redirectAttributes.addFlashAttribute("success", "Category saved successfully");
        } catch (DataIntegrityViolationException e1) {
            e1.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Category name already exists");
        }
        catch (Exception e2) {
            e2.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Something went wrong");
        }

        return "redirect:/categories";
    }
@GetMapping("/update-category")
    public String UpdareCategory(@ModelAttribute("categoryupdated") Category category, Model model, RedirectAttributes redirectAttributes) {
try {

    categoryService.updatecategory(category);



    redirectAttributes.addFlashAttribute("success", "Category updated successfully");

}
catch (DataIntegrityViolationException e1) {
    e1.printStackTrace();
    redirectAttributes.addFlashAttribute("error", "Category name already exists");
}
catch (Exception e2) {
    e2.printStackTrace();
    redirectAttributes.addFlashAttribute("error", "Something went wrong");
}

    return "redirect:/categories";
    }
    @RequestMapping(value = "/deleteCategory", method = {RequestMethod.PUT, RequestMethod.GET})
    public String deleteCategory(Long id, RedirectAttributes redirectAttributes) {

     try{
         categoryService.deletecategory(id);
         redirectAttributes.addFlashAttribute("success", "Category deleted successfully");
     }
     catch (Exception e){
         e.printStackTrace();
         redirectAttributes.addFlashAttribute("error", "Something went wrong");
     }
        return "redirect:/categories";
    }
    @RequestMapping(value = "/enableCategory", method = {RequestMethod.PUT, RequestMethod.GET})
    public String enableCategory(Long id, RedirectAttributes redirectAttributes) {

        try{
            categoryService.enableById(id);
            redirectAttributes.addFlashAttribute("success", "Category enabled successfully");
        }
        catch (Exception e){
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Something went wrong");
        }
        return "redirect:/categories";
    }


}
