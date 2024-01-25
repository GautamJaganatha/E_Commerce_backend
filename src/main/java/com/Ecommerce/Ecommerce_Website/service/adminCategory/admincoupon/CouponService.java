package com.Ecommerce.Ecommerce_Website.service.adminCategory.admincoupon;

import com.Ecommerce.Ecommerce_Website.entity.Coupon;

import java.util.List;

public interface CouponService {
    Coupon createCoupon(Coupon coupon);

    List<Coupon> getAllCoupons();
}
