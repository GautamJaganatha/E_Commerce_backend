package com.Ecommerce.Ecommerce_Website.controller.admin;

import com.Ecommerce.Ecommerce_Website.dto.ProductDto;
import com.Ecommerce.Ecommerce_Website.entity.Product;
import com.Ecommerce.Ecommerce_Website.service.adminCategory.adminProduct.AdminProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/product")
public class AdminProductController {

    private final AdminProductService adminProductService;


    @PostMapping("/addProduct")
    public ResponseEntity<ProductDto> addProduct(@ModelAttribute ProductDto productDto) throws IOException {
        ProductDto productDto1 = adminProductService.addProduct(productDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(productDto1);
    }


    @GetMapping("/getProducts")
    public ResponseEntity<List<ProductDto>> getAllProduct(){
        List<ProductDto> productDtos = adminProductService.getAllProducts();
        return ResponseEntity.ok(productDtos);
    }


    @GetMapping("/search{name}")
    public ResponseEntity<List<ProductDto>> getAllProductByName(@PathVariable String name){
        List<ProductDto> productDtos = adminProductService.getAllProducts();
        return ResponseEntity.ok(productDtos);
    }
}
