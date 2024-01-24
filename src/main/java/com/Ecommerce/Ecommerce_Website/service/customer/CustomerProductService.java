package com.Ecommerce.Ecommerce_Website.service.customer;

import com.Ecommerce.Ecommerce_Website.dto.ProductDto;

import java.util.List;

public interface CustomerProductService {


    public List<ProductDto> getAllProducts();

    public List<ProductDto> searchProductByTitle(String name);
}
