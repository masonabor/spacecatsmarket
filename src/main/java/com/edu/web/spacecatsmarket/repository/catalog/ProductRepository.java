package com.edu.web.spacecatsmarket.repository.catalog;

import com.edu.web.spacecatsmarket.repository.catalog.entity.CategoryEntity;
import com.edu.web.spacecatsmarket.repository.catalog.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, UUID> {

    boolean existsByName(String name);

    List<ProductEntity> findByCategoriesContaining(CategoryEntity category);

    @Query("SELECT p.name as productName, SUM(oi.quantity) as totalSold " +
            "FROM OrderItemEntity oi " +
            "JOIN oi.product p " +
            "GROUP BY p.name " +
            "ORDER BY totalSold DESC")
    List<TopProductProjection> findTopSellingProducts();

    @Modifying
    @Transactional
    @Query("UPDATE ProductEntity p SET p.amount = p.amount + :amount WHERE p.id = :id")
    void addToAmount(@Param("id") UUID id, @Param("amount") int amount);

    @Modifying
    @Transactional
    @Query("UPDATE ProductEntity p SET p.amount = p.amount - :amount WHERE p.id = :id")
    void removeFromAmount(@Param("id") UUID id, @Param("amount") int amount);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO product_categories (product_id, category_id) VALUES (:productId, :categoryId)", nativeQuery = true)
    void addCategory(@Param("productId") UUID productId, @Param("categoryId") UUID categoryId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM product_categories WHERE product_id = :productId AND category_id = :categoryId", nativeQuery = true)
    void removeCategory(@Param("productId") UUID productId, @Param("categoryId") UUID categoryId);
}