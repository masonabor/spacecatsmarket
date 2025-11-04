package com.edu.web.spacecatsmarket.config;

import com.edu.web.spacecatsmarket.featuretoggle.FeatureToggles;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@ConfigurationProperties("application.feature")
@NoArgsConstructor
@Getter
@Setter
public class FeatureToggleConfig {

    Map<FeatureToggles, Boolean> featureToggles;

    public boolean isFeatureToggleEnabled(FeatureToggles featureToggle) {
        return featureToggles.get(featureToggle);
    }
}