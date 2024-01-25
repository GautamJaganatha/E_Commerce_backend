package com.Ecommerce.Ecommerce_Website.controller.admin;

import com.Ecommerce.Ecommerce_Website.entity.Coupon;
import com.Ecommerce.Ecommerce_Website.exceptions.ValidationException;
import com.Ecommerce.Ecommerce_Website.service.adminCategory.admincoupon.CouponService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/coupons")
@RequiredArgsConstructor
public class AdminCouponController {
    private final CouponService couponService;

    @PostMapping("/createCoupon")
    public ResponseEntity<?> createCoupon(@RequestBody Coupon coupon){
        try {
            Coupon createdCoupon = couponService.createCoupon(coupon);
            return ResponseEntity.ok(createdCoupon);
        }
        catch (ValidationException ex){
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }


    @GetMapping("/getAllCoupons")
    public ResponseEntity<List<Coupon>> getAllCoupons(){
        return ResponseEntity.ok(couponService.getAllCoupons());
    }
}
