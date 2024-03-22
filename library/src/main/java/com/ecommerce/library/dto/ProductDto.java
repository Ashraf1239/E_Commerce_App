package com.ecommerce.library.dto;

import com.ecommerce.library.model.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;

import java.math.BigDecimal;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
private Long id;

    private String name;
    private String description;
    private int currentQuantity;
    private double  costPrice;
    private  double  salePrice;
    private  byte[] image;
    private Category category;
    private  Boolean is_actived;
    private  Boolean is_deleted;

}
