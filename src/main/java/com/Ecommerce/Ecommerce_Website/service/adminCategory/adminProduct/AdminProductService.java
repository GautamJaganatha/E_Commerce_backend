package com.Ecommerce.Ecommerce_Website.service.adminCategory.adminProduct;

import com.Ecommerce.Ecommerce_Website.dto.ProductDto;

import java.io.IOException;
import java.util.List;

public interface AdminProductService {
    public ProductDto addProduct(ProductDto productDto) throws IOException;

    public List<ProductDto> getAllProducts();

    List<ProductDto> getAllProductsByName(String name);
}
