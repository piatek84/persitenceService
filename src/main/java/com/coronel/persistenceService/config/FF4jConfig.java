package com.coronel.persistenceService.config;

import org.ff4j.FF4j;
import org.ff4j.core.Feature;
import org.ff4j.core.FlippingStrategy;
import org.ff4j.strategy.time.ReleaseDateFlipStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class FF4jConfig {

    public static final String CONSUMER_ENABLED = "consumer-enabled";
    public static final List<String> features = List.of(CONSUMER_ENABLED);

    @Bean
    public FF4j ff4j() {
        FF4j ff4j = new FF4j();
        for (String f : features) {
            Feature feature = new Feature(f);
//        We can enable the feature after a date
//        feature.setEnable(true);
//        FlippingStrategy strategy = new ReleaseDateFlipStrategy("2025-03-23-18:55");
//        feature.setFlippingStrategy(strategy);
            ff4j.createFeature(feature);
        }


        return ff4j;
    }
}
