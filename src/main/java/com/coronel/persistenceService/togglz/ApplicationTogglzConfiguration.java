package com.coronel.persistenceService.togglz;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.togglz.core.context.StaticFeatureManagerProvider;
import org.togglz.core.manager.FeatureManager;
import org.togglz.core.manager.FeatureManagerBuilder;
import org.togglz.core.manager.TogglzConfig;

@Configuration
public class ApplicationTogglzConfiguration {
    @Bean
    FeatureManager featureManager() {
        TogglzConfig togglzConfig = new TogglzConfiguration();
        FeatureManager featureManager = new FeatureManagerBuilder().togglzConfig(togglzConfig).build();
        StaticFeatureManagerProvider.setFeatureManager(featureManager);
        return featureManager;
    }
}