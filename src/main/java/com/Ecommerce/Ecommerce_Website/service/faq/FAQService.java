package com.Ecommerce.Ecommerce_Website.service.faq;

import com.Ecommerce.Ecommerce_Website.dto.FAQDto;

public interface FAQService {

    FAQDto postFAQ(Long productId, FAQDto faqDto);
}
