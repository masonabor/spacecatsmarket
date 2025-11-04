package com.edu.web.spacecatsmarket.service;

import com.edu.web.spacecatsmarket.featuretoggle.FeatureToggles;
import com.edu.web.spacecatsmarket.featuretoggle.annotation.FeatureToggle;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CosmoCatsService {

    @FeatureToggle(FeatureToggles.COSMO_CATS)
    public List<String> getCosmoCats() {
        return List.of("Luna", "Orion", "Nebula", "Comet", "Stella");
    }
}
