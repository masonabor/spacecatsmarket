package com.edu.web.spacecatsmarket.featuretoggle;

import lombok.Getter;

@Getter
public enum FeatureToggles {

    COSMO_CATS("cosmoCats"),
    FILE_LOGGING("fileLogging"),
    ADMIN_DELETION("adminDeletion");

    private final String name;

    FeatureToggles(String name) {
        this.name = name;
    }

}
