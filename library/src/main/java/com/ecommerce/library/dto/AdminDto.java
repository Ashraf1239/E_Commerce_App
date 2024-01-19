package com.ecommerce.library.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminDto {
    @Size(min=3,max=10,message = "Invalid first name")
    private String firstName;
    @Size(min=3,max=10,message = "Invalid first name")
    private String lastName ;
    private String username;
    @Size(min = 5, max = 10, message = "Password contains 5-10 characters")
    private String password;
    @Size(min = 5, max = 10, message = "Password contains 5-10 characters")
    private String repassword;
}
