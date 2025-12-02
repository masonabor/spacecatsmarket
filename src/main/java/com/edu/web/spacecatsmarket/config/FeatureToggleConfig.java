package com.edu.web.spacecatsmarket.config;

import com.edu.web.spacecatsmarket.featuretoggle.FeatureToggles;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "application.feature")
@NoArgsConstructor
@Data
public class FeatureToggleConfig {

    Map<FeatureToggles, Boolean> featureToggles;
}