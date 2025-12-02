package com.edu.web.spacecatsmarket.config;

import com.edu.web.spacecatsmarket.featuretoggle.FeatureToggles;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "application.feature")
@NoArgsConstructor
@Getter
public class FeatureToggleConfig {

    Map<FeatureToggles, Boolean> featureToggles;
}