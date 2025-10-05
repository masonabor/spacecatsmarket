package com.edu.web.spacecatsmarket.catalog.domain;

import java.util.List;
import java.util.Optional;

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
    List<Product> findAll();
    Optional<Product> findById(ProductId id);
    void delete(ProductId id);
    List<Product> findAllByCategory(Category category);
    boolean existByName(ProductName productName);
}
