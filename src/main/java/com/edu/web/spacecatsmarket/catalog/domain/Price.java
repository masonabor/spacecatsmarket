package com.edu.web.spacecatsmarket.catalog.domain;

import org.springframework.util.Assert;

public record Price(Double price) {

    /**
     Тут доречно використовувати record для збереження ціни продукту,
     адже імутабельність + валідація через Assert без залежності домену від зовнішніх кастомних валідаторів*/

    public Price {
        Assert.notNull(price, "price must not be null");
        Assert.isTrue(price >= 0, "price must be >= 0");
    }
}
