package com.Ecommerce.Ecommerce_Website.service.adminCategory;

import com.Ecommerce.Ecommerce_Website.dto.CategoryDto;
import com.Ecommerce.Ecommerce_Website.entity.Category;

import java.util.List;

public interface CategoryService {
    Category createCategory(CategoryDto categoryDto);

    List<Category> getAllCategories();
}
