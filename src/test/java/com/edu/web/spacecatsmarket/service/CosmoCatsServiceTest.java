package com.edu.web.spacecatsmarket.service;

import com.edu.web.spacecatsmarket.featuretoggle.FeatureToggles;
import com.edu.web.spacecatsmarket.featuretoggle.exception.FeatureCosmoCatsNotEnabledException;
import com.edu.web.spacecatsmarket.featuretoggle.impl.FeatureToggleServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = {CosmoCatsService.class, FeatureToggleServiceImpl.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Cosmo cats service Tests")
public class CosmoCatsServiceTest {

    @Autowired
    private CosmoCatsService cosmoCatsService;

    @Autowired
    private FeatureToggleServiceImpl featureToggleService;

    @Test
    @DisplayName("Should return list of cats when cosmo-cats feature is enabled")
    void shouldReturnCosmoCatsWhenFeatureEnabled() {
        featureToggleService.enable(FeatureToggles.COSMO_CATS);

        var cats = cosmoCatsService.getCosmoCats();

        assertNotNull(cats);
        assertTrue(cats.contains("Luna"));
    }

    @Test
    @DisplayName("Should throw FeatureCosmoCatsNotEnableException when feature cosmo-cats is disabled")
    void shouldThrowFeatureCosmoCatsNotEnableExceptionWhenFeatureDisabled() {
        featureToggleService.disable(FeatureToggles.COSMO_CATS);

        assertThrows(FeatureCosmoCatsNotEnabledException.class, () -> cosmoCatsService.getCosmoCats());
    }

}
