package com.edu.web.spacecatsmarket.repository.catalog;


import com.edu.web.spacecatsmarket.domain.catalog.Category;
import com.edu.web.spacecatsmarket.domain.catalog.Product;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository {

    /**
     * 1. Репозиторії оперують тільки доменними об'єктами (Aggregate Roots).
     * 2. Ніяких DTO тут бути не повинно (вони належать application/web шару).
     * 3. Стандартний набір методів
     * 4. Оновлення робиться через:
     *    - завантаження об’єкта з репозиторію
     *    - виклик методів бізнес-логіки на Aggregate Root
     *    - повторне збереження в репозиторій
     */

    void save(Product product);
    void update(Product product);
    void saveAll(HashMap<UUID, Product> products);
    List<Product> findAll();
    Optional<Product> findById(UUID id);
    void delete(UUID id);
    boolean existByName(String productName);
    List<Product> findAllByCategory(Category category);
}
