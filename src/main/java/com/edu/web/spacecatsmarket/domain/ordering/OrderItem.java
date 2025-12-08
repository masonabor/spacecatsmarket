package com.edu.web.spacecatsmarket.domain.ordering;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
public class OrderItem {

    UUID id;
    UUID orderId;
    UUID productId;
    Integer quantity;
    Double snapshotPrice;
}
