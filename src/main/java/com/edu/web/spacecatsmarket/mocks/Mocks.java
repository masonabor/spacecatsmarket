package com.edu.web.spacecatsmarket.mocks;

import com.edu.web.spacecatsmarket.catalog.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class Mocks {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Bean
    public ApplicationRunner initMocks() {
        return _ -> {
            var category1 = new Category(CategoryId.newId(), new CategoryName("1"));
            var category2 = new Category(CategoryId.newId(), new CategoryName("2"));
            categoryRepository.save(category1);
            categoryRepository.save(category2);

            productRepository.save(Product.builder()
                    .id(ProductId.newId())
                    .name(new ProductName("Product1"))
                    .description("lorem ipsum dolor sit amet")
                    .price(new Price(123.4))
                    .amount(new ProductAmount(100))
                    .categories(Set.of(category1, category2))
                    .build());
        };
    }
}
