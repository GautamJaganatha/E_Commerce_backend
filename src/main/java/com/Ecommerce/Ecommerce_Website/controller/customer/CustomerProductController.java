package com.Ecommerce.Ecommerce_Website.controller.customer;

import com.Ecommerce.Ecommerce_Website.dto.ProductDto;
import com.Ecommerce.Ecommerce_Website.service.customer.CustomerProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/admin/customer")
@RequiredArgsConstructor
public class CustomerProductController {


    private final CustomerProductService customerProductService;


    @GetMapping("/getProducts")
    public ResponseEntity<List<ProductDto>> getAllProduct(){
        List<ProductDto> productDtos = customerProductService.getAllProducts();
        return ResponseEntity.ok(productDtos);
    }


    @GetMapping("/search{name}")
    public ResponseEntity<List<ProductDto>> getAllProductByName(@PathVariable String name){
        List<ProductDto> productDtos = customerProductService.searchProductByTitle(name);
        return ResponseEntity.ok(productDtos);
    }


}
