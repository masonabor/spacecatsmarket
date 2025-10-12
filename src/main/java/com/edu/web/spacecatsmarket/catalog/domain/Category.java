package com.edu.web.spacecatsmarket.catalog.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "id")
@ToString(of = {"id", "name"})
public class Category {

    private CategoryId id;
    private CategoryName name;

    public static Category create(String name) {
        return new Category(CategoryId.newId(), new CategoryName(name));
    }

    public void changeName(CategoryName newName) {
        this.name = newName;
    }
}