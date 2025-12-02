package com.edu.web.spacecatsmarket.featuretoggle.aspect;

import com.edu.web.spacecatsmarket.featuretoggle.FeatureToggleService;
import com.edu.web.spacecatsmarket.featuretoggle.FeatureToggles;
import com.edu.web.spacecatsmarket.featuretoggle.annotation.FeatureToggle;
import com.edu.web.spacecatsmarket.featuretoggle.exception.FeatureCosmoCatsNotEnabledException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class CosmoCatsAspect {

    public final FeatureToggleService featureToggleService;

    @Around(value = "@annotation(featureToggle)")
    public Object checkCosmoCatsFeatureToggle(ProceedingJoinPoint joinPoint, FeatureToggle featureToggle) throws Throwable {
        FeatureToggles featureToggles = featureToggle.value();

        if (featureToggles == FeatureToggles.COSMO_CATS) {
            if (!featureToggleService.check(featureToggles)) {
                log.warn("Cosmo Cats feature is not enabled: {}", joinPoint.getSignature().toShortString());
                throw new FeatureCosmoCatsNotEnabledException("Cosmo Cats feature is not enabled");
            }
        }

        return joinPoint.proceed();
    }
}
