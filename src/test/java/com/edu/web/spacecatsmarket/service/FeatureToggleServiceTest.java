package com.edu.web.spacecatsmarket.service;

import com.edu.web.spacecatsmarket.config.FeatureToggleConfig;
import com.edu.web.spacecatsmarket.featuretoggle.FeatureToggles;
import com.edu.web.spacecatsmarket.featuretoggle.impl.FeatureToggleServiceImpl;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Feature Toggle Service Unit Tests")
class FeatureToggleServiceTest {

    @Mock
    private FeatureToggleConfig featureToggleConfig;

    private FeatureToggleServiceImpl featureToggleService;

    private final FeatureToggles EXISTING_TOGGLE = FeatureToggles.ADMIN_OPERATION;

    @BeforeEach
    void setUp() {
        Map<FeatureToggles, Boolean> initialMap = new HashMap<>();
        initialMap.put(EXISTING_TOGGLE, true);
        when(featureToggleConfig.getFeatureToggles()).thenReturn(initialMap);

        featureToggleService = new FeatureToggleServiceImpl(featureToggleConfig);
    }

    @Test
    @DisplayName("Check returns TRUE when toggle is initially enabled")
    void checkShouldReturnTrueWhenToggleIsEnabledInitially() {
        assertTrue(featureToggleService.check(EXISTING_TOGGLE));
    }

    @Test
    @DisplayName("Check returns FALSE when toggle is not present in config")
    void checkShouldReturnFalseWhenToggleIsNotPresent() {
        assertFalse(featureToggleService.check(FeatureToggles.COSMO_CATS));
    }

    @Test
    @DisplayName("Enable successfully switches toggle from disabled to enabled")
    void enableShouldEnableToggle() {
        featureToggleService.disable(EXISTING_TOGGLE);
        featureToggleService.enable(EXISTING_TOGGLE);
        assertTrue(featureToggleService.check(EXISTING_TOGGLE));
    }

    @Test
    @DisplayName("Disable successfully switches toggle from enabled to disabled")
    void disableShouldDisableToggle() {
        featureToggleService.disable(EXISTING_TOGGLE);
        assertFalse(featureToggleService.check(EXISTING_TOGGLE));
    }

    @Test
    @DisplayName("Constructor handles null config map gracefully")
    void constructorShouldHandleNullConfigMap() {
        when(featureToggleConfig.getFeatureToggles()).thenReturn(null);

        FeatureToggleServiceImpl safeService = new FeatureToggleServiceImpl(featureToggleConfig);

        assertFalse(safeService.check(EXISTING_TOGGLE));
    }
}