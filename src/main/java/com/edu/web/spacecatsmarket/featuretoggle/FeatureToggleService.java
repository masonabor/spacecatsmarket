package com.edu.web.spacecatsmarket.featuretoggle;

public interface FeatureToggleService {
    boolean check(FeatureToggles featureToggle);
    void enable(FeatureToggles featureToggle);
    void disable(FeatureToggles featureToggle);
}
