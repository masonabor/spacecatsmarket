package com.edu.web.spacecatsmarket.featuretoggle.impl;

import com.edu.web.spacecatsmarket.config.FeatureToggleConfig;
import com.edu.web.spacecatsmarket.featuretoggle.FeatureToggleService;
import com.edu.web.spacecatsmarket.featuretoggle.FeatureToggles;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class FeatureToggleServiceImpl implements FeatureToggleService {

    private final ConcurrentHashMap<FeatureToggles, Boolean> featureToggleMap;

    public FeatureToggleServiceImpl(FeatureToggleConfig featureToggleConfig) {
        Map<FeatureToggles, Boolean> map = featureToggleConfig.getFeatureToggles();
        featureToggleMap = new ConcurrentHashMap<>(map != null ? map : Map.of());
    }

    @Override
    public boolean check(FeatureToggles featureToggles) {
        return featureToggleMap.getOrDefault(featureToggles, false);
    }

    @Override
    public void enable(FeatureToggles featureToggles) {
        featureToggleMap.put(featureToggles, true);
    }

    @Override
    public void disable(FeatureToggles featureToggles) {
        featureToggleMap.put(featureToggles, false);
    }
}
