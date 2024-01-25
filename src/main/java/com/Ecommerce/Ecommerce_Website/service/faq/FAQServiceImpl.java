package com.Ecommerce.Ecommerce_Website.service.faq;

import com.Ecommerce.Ecommerce_Website.dto.FAQDto;
import com.Ecommerce.Ecommerce_Website.entity.FAQ;
import com.Ecommerce.Ecommerce_Website.entity.Product;
import com.Ecommerce.Ecommerce_Website.repo.FAQRepository;
import com.Ecommerce.Ecommerce_Website.repo.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FAQServiceImpl implements FAQService{
    private final FAQRepository faqRepository;

    private final ProductRepository productRepository;

    public FAQDto postFAQ(Long productId, FAQDto faqDto){
        Optional<Product> optionalProduct =productRepository.findById(productId);
        if (optionalProduct.isPresent()){
            FAQ faq = new FAQ();
            faq.setQuestion(faqDto.getQuestion());
            faq.setAnswer(faqDto.getAnswer());
            faq.setProduct(optionalProduct.get());

            return faqRepository.save(faq).getFAQDto();

        }
        return null;
    }
}
