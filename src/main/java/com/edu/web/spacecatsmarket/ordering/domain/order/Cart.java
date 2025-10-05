package com.edu.web.spacecatsmarket.ordering.domain.order;

import com.edu.web.spacecatsmarket.ordering.domain.CustomerDetailsId;
import lombok.Builder;

import java.util.HashSet;
import java.util.Set;

@Builder
public class Cart {

    CartId id;

    @Builder.Default
    Set<CartItem> items = new HashSet<>();

    CustomerDetailsId customerId;


}
