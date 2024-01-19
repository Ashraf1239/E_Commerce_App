package com.ecommerce.library.service;

import com.ecommerce.library.dto.AdminDto;
import com.ecommerce.library.model.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ecommerce.library.repository.AdminRepository;
import com.ecommerce.library.repository.RuleRepository;

import java.util.Arrays;

@Service
public class AdminService {
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private RuleRepository ruleRepository;
    public Admin findbyname(String name){
        return adminRepository.findByUsername(name);
    }
    public Admin save(AdminDto adminDto )
    {
        Admin admin=new Admin();
        admin.setFirstName(adminDto.getFirstName());
        admin.setLastName(adminDto.getLastName());
        admin.setUsername(adminDto.getUsername());
        admin.setPassword(adminDto.getPassword());
        admin.setRoles(Arrays.asList(ruleRepository.findByName("ADMIN")));
        return adminRepository.save(admin);
    }

}
