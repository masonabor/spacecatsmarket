package com.edu.web.spacecatsmarket.ordering.domain.order;

import com.edu.web.spacecatsmarket.catalog.domain.Product;
import org.springframework.util.Assert;

public record CartItem(Product product, Integer amount) {

    public CartItem {
        Assert.notNull(product, "product must not be null");
        Assert.notNull(amount, "amount must not be null");
        Assert.isTrue(amount > 0, "amount must be greater than 0");
    }
}
