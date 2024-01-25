package com.Ecommerce.Ecommerce_Website.service.adminCategory.admincoupon;

import com.Ecommerce.Ecommerce_Website.entity.Coupon;
import com.Ecommerce.Ecommerce_Website.exceptions.ValidationException;
import com.Ecommerce.Ecommerce_Website.repo.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService{
    private final CouponRepository couponRepository;

    public Coupon createCoupon( Coupon coupon){
        if (couponRepository.existsByCode(coupon.getCode())){
            throw new ValidationException("Coupon code already exists");
        }
        return couponRepository.save(coupon);
    }

    public List<Coupon> getAllCoupons(){
        return couponRepository.findAll();
    }
}
