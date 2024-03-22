package com.ecommerce.library.service;

import com.ecommerce.library.dto.ProductDto;
import com.ecommerce.library.model.Product;
import com.ecommerce.library.repository.ProductRepository;
import com.ecommerce.library.utils.ImageUpload;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Base64;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ImageUpload imageUpload;
    ModelMapper modelMapper = new ModelMapper();
    public List<ProductDto> allProducts() {
        List<Product> products = productRepository.findAll();
        Type listType = new TypeToken<List<ProductDto>>() {}.getType();
        List<ProductDto> productDTOs = modelMapper.map(products, listType);
        return productDTOs;
    }
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }
    public ProductDto getProductById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        return modelMapper.map(product, ProductDto.class);
    }

    public Product saveProduct(ProductDto productDto, MultipartFile imageProduct) {
        Product product = modelMapper.map(productDto, Product.class);
        product.setIs_actived(true);
        product.setIs_deleted(false);

        if (imageProduct != null) {
            boolean isUpload = imageUpload.uploadImage(imageProduct);

            if (isUpload) {
                try {

                    product.setImage(imageProduct.getBytes());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        } else {
            product.setImage(null);
        }

        return productRepository.save(product);
    }


    public Product updateProduct(ProductDto productDto, MultipartFile imageProduct, Long id) throws IOException {
        // Retrieve the existing Product entity from the database using the custom method

        Product existingProduct = productRepository.getReferenceById(id);
        productDto.setIs_actived(existingProduct.getIs_actived());
        productDto.setIs_deleted(existingProduct.getIs_deleted());

        // Map properties from ProductDto to existing Product
        modelMapper.map(productDto, existingProduct);

        // Update the image if a new one is provided
        if (imageProduct != null && imageProduct.getBytes().length > 0) {
            if (!imageUpload.checkExist(imageProduct)) {
                imageUpload.uploadImage(imageProduct);
                existingProduct.setImage(imageProduct.getBytes());
            }
        }

        // Save the updated Product entity
        return productRepository.save(existingProduct);
    }

    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id).orElse(null);
        product.setIs_deleted(true);
        product.setIs_actived(false);
        productRepository.save(product);

    }
    public void enableProduct(Long id) {
        Product product = productRepository.findById(id).orElse(null);
        product.setIs_deleted(false);
        product.setIs_actived(true);
        productRepository.save(product);
    }

    public Page<Product> pageProducts(int page) {
        Pageable pageable = PageRequest.of(page, 5);
        return  productRepository.pageProducts(pageable);


    }
    public Page<Product> searchProduct(String name, int page) {
        Pageable pageable = PageRequest.of(page, 5);
        return  productRepository.searchProduct(name, pageable);
    }

    public List<Product> getAllProducts() {
        return productRepository.getAllProducts();
    }

    public List<Product> ListViewProducts() {
        return productRepository.ListViewProducts();
    }


    public Product getProductById2(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        return product;
    }
    public List<Product> getRelatedProducts(Long categoryId) {
        List <Product>products= productRepository.getRelatedProducts(categoryId);
    return products;
    }
    public List<Product> getProductinCategory(Long categoryId) {
        List <Product>products= productRepository.getProductinCategory(categoryId);
        return products;
    }
    public List<ProductDto> getFilterByLower() {
        List <Product>products= productRepository.filterByLower();
        Type listType = new TypeToken<List<ProductDto>>() {}.getType();
        List<ProductDto> productDTOs = modelMapper.map(products, listType);
        return productDTOs;
    }
    public List<ProductDto> getFilterByUpper() {
        List <Product>products= productRepository.filterByHigher();
        Type listType = new TypeToken<List<ProductDto>>() {}.getType();
        List<ProductDto> productDTOs = modelMapper.map(products, listType);
        return productDTOs;
    }


}
