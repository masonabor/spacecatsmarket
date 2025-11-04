package com.edu.web.spacecatsmarket.featuretoggle;

import lombok.Getter;

@Getter
public enum FeatureToggles {

    COSMO_CATS("cosmo-cats"),
    ADMIN_OPERATION("admin-operation");

    private final String name;

    FeatureToggles(String name) {
        this.name = name;
    }

}
