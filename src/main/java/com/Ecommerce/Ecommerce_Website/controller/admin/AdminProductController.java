package com.Ecommerce.Ecommerce_Website.controller.admin;

import com.Ecommerce.Ecommerce_Website.dto.FAQDto;
import com.Ecommerce.Ecommerce_Website.dto.ProductDto;
import com.Ecommerce.Ecommerce_Website.entity.Product;
import com.Ecommerce.Ecommerce_Website.service.adminCategory.adminProduct.AdminProductService;
import com.Ecommerce.Ecommerce_Website.service.faq.FAQService;
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

    private final FAQService faqService;


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
        List<ProductDto> productDtos = adminProductService.getAllProductsByName(name);
        return ResponseEntity.ok(productDtos);
    }


    @DeleteMapping("/product/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id){
        boolean deleted = adminProductService.deleteProduct(id);
        if (deleted){
           return ResponseEntity.noContent().build();
        }
       return ResponseEntity.notFound().build();
    }

    @PostMapping("/faq/{productId}")
    public ResponseEntity<FAQDto> postFAQ(@PathVariable Long productId, @RequestBody FAQDto faqDto){
        return ResponseEntity.ok(faqService.postFAQ(productId,faqDto));
    }


}
