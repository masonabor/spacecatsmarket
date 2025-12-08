package com.edu.web.spacecatsmarket.repository.ordering;

import com.edu.web.spacecatsmarket.repository.ordering.entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CartRepository extends JpaRepository<CartEntity, UUID> {
}