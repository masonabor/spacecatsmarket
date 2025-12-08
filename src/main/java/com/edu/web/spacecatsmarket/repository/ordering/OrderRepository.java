package com.edu.web.spacecatsmarket.repository.ordering;

import com.edu.web.spacecatsmarket.domain.ordering.Order;
import com.edu.web.spacecatsmarket.repository.ordering.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, UUID> {

    Optional<Order> findByTransactionId(UUID transactionId);
}