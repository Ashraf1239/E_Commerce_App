package com.ecommerce.admin.control;

import com.ecommerce.library.dto.AdminDto;
import com.ecommerce.library.model.Admin;
import com.ecommerce.library.service.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class LoginControl {
    @Autowired
    private AdminService adminService;
    private final BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("title", "Login Page");
        return "login";
    }
    @RequestMapping("/index")
public String home(Model model){
        model.addAttribute("title", "Home Page");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "redirect:/login";
        }

        return  "index";
    }
    @GetMapping("/register")
    public String reqister(Model model) {

        model.addAttribute("title", "Register");
        model.addAttribute("adminDto", new AdminDto());
        return "register";
    }

    @GetMapping("/forgot-password")
    public String forgotPassword(Model model) {
        model.addAttribute("title", "Forgot Password");
        return "forgot-password";
    }

    @PostMapping("/register-new")
    public String addnewadmin(@Valid @ModelAttribute("adminDto") AdminDto adminDto
            , BindingResult bindingResult
            , Model model

    ) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("adminDto", adminDto);
            return "register";
        }
        String username = adminDto.getUsername();
        Admin admin = adminService.findbyname(username);
        if (admin != null) {
            model.addAttribute("adminDto", adminDto);
            model.addAttribute("emailError", "Your email has been registered!");
            return "register";
        }
        if (adminDto.getPassword().equals(adminDto.getRepassword())) {
          adminDto.setPassword(passwordEncoder.encode(adminDto.getPassword()));
            adminService.save(adminDto);
            model.addAttribute("success", "Register successfully!");
            model.addAttribute("adminDto", adminDto);
            return "register";
        } else {

            model.addAttribute("adminDto", adminDto);
            model.addAttribute("passwordError", "Your password maybe wrong! Check again!");
            return "register";
        }

    }
}
