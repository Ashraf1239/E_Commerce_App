package com.ecommerce.library.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.tomcat.util.codec.binary.Base64;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;

import java.math.BigDecimal;
import java.sql.Types;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "products", uniqueConstraints = @UniqueConstraint(columnNames = {"name"}))
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;
    private String name;
    private String description;
    private int currentQuantity;
    private double  costPrice;
    private  double  salePrice =0.0;
    @Lob
    @JdbcTypeCode(Types.BINARY)
    private byte[] image;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", referencedColumnName = "category_id")
    private  Category category;
    @Column(columnDefinition = "boolean default true")
    private  Boolean is_actived = true;
    @Column(columnDefinition = "boolean default false")
    private  Boolean is_deleted= false;


}
