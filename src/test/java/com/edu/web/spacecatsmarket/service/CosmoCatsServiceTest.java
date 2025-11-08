package com.edu.web.spacecatsmarket.service;

import com.edu.web.spacecatsmarket.featuretoggle.FeatureToggleService;
import com.edu.web.spacecatsmarket.featuretoggle.FeatureToggles;
import com.edu.web.spacecatsmarket.featuretoggle.aspect.CosmoCatsAspect;
import com.edu.web.spacecatsmarket.featuretoggle.exception.FeatureCosmoCatsNotEnabledException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {CosmoCatsService.class, CosmoCatsAspect.class})
@EnableAspectJAutoProxy
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Cosmo cats service Tests")
public class CosmoCatsServiceTest {

    @Autowired
    private CosmoCatsService cosmoCatsService;

    @MockitoBean
    private FeatureToggleService featureToggleService;

    @Test
    @DisplayName("Should return list of cats when cosmo-cats feature is enabled")
    void shouldReturnCosmoCatsWhenFeatureEnabled() {

        when(featureToggleService.check(FeatureToggles.COSMO_CATS)).thenReturn(true);

        var cats = cosmoCatsService.getCosmoCats();

        assertNotNull(cats);
        assertTrue(cats.contains("Luna"));
    }

    @Test
    @DisplayName("Should throw FeatureCosmoCatsNotEnableException when feature cosmo-cats is disabled")
    void shouldThrowFeatureCosmoCatsNotEnableExceptionWhenFeatureDisabled() {

        when(featureToggleService.check(FeatureToggles.COSMO_CATS)).thenReturn(false);

        assertThrows(FeatureCosmoCatsNotEnabledException.class, () -> cosmoCatsService.getCosmoCats());
    }

}
