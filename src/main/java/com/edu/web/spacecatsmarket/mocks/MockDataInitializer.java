package com.edu.web.spacecatsmarket.mocks;

import com.edu.web.spacecatsmarket.repository.catalog.CategoryRepository;
import com.edu.web.spacecatsmarket.repository.catalog.ProductRepository;
import com.edu.web.spacecatsmarket.repository.catalog.entity.CategoryEntity;
import com.edu.web.spacecatsmarket.repository.catalog.entity.ProductEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Profile("dev")
@Component
@RequiredArgsConstructor
public class MockDataInitializer implements ApplicationRunner {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        var category1 = CategoryEntity.builder()
                .id(UUID.randomUUID())
                .name("1")
                .build();
        var category2 = CategoryEntity.builder()
                .id(UUID.randomUUID())
                .name("2")
                .build();

        categoryRepository.save(category1);
        categoryRepository.save(category2);

        productRepository.save(ProductEntity.builder()
                .id(UUID.randomUUID())
                .name("Product1")
                .description("lorem ipsum dolor sit amet")
                .price(123.4)
                .amount(100)
                .categories(new HashSet<>(Set.of(category1, category2)))
                .build());
    }
}