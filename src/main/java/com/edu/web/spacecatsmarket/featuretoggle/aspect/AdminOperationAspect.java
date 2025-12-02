package com.edu.web.spacecatsmarket.featuretoggle.aspect;

import com.edu.web.spacecatsmarket.featuretoggle.FeatureToggleService;
import com.edu.web.spacecatsmarket.featuretoggle.FeatureToggles;
import com.edu.web.spacecatsmarket.featuretoggle.annotation.FeatureToggle;
import com.edu.web.spacecatsmarket.featuretoggle.exception.AdminRequiredException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class AdminOperationAspect {

    private final FeatureToggleService featureToggleService;
    private final HttpServletRequest request;

    @Before(value = "@annotation(featureToggle)")
    public void checkAdminDeletionFeature(FeatureToggle featureToggle) {

        FeatureToggles featureToggles = featureToggle.value();

        if (featureToggleService.check(featureToggles)) {
            checkAdminRole(featureToggles);
        }
    }

    private void checkAdminRole(FeatureToggles featureToggles) {
        if (featureToggles.equals(FeatureToggles.ADMIN_OPERATION)) {
            String role = request.getHeader("role");

            if (role == null || !role.equalsIgnoreCase("admin")) {
                log.warn("You don`t have access with role: {}", role);
                throw new AdminRequiredException("You don't have permission to perform this operation");
            }
        }
    }
}
