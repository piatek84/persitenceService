package com.coronel.persistenceService.togglz;

import org.togglz.core.Feature;
import org.togglz.core.context.FeatureContext;

public enum MyFeatures implements Feature {

    CONSUMER;

    public boolean isActive() {
        return FeatureContext.getFeatureManager().isActive(this);
    }
}