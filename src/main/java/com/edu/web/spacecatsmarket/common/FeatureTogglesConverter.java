package com.edu.web.spacecatsmarket.common;

import com.edu.web.spacecatsmarket.featuretoggle.FeatureToggles;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.awt.*;

@Component
public class FeatureTogglesConverter implements Converter<String, FeatureToggles> {

    @Override
    public FeatureToggles convert(String source) {
        return FeatureToggles.valueOf(source.toLowerCase());
    }
}
